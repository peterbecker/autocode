package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.Person;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("WeakerAccess")
@Slf4j
public class TestClient {
    private final Client httpClient;
    private final String baseUrl;

    public TestClient(Client client, int sutPort) {
        this.httpClient = client;
        this.baseUrl = String.format("http://localhost:%d/", sutPort);
    }

    public Response get(String path) {
        return httpClient.target(baseUrl + path).request().get();
    }

    public <T> Response post(String path, T body) {
        return httpClient.target(baseUrl + path).request().post(Entity.json(body));
    }

    public <T> Response put(String path, T body) {
        return httpClient.target(baseUrl + path).request().put(Entity.json(body));
    }

    public Response delete(String path) {
        return httpClient.target(baseUrl + path).request().delete();
    }

    public List<Person> getAllPersons() {
        Response response = get("person");
        assertThat(response.getStatus()).isEqualTo(200);
        return response.readEntity(new GenericType<>(){});
    }

    public Person getPerson(long id) {
        Response response = get("person/" + id);
        assertThat(response.getStatus()).isEqualTo(200);
        return response.readEntity(Person.class);
    }

    public long addPerson(String name, LocalDate birthday) {
        Person person = new Person(name, birthday);
        Response response = post("person", person);
        assertThat(response.getStatus()).isEqualTo(201);
        String prefix = baseUrl + "person/";
        String location = response.getHeaderString("location");
        assertThat(location).startsWith(prefix);
        String idAsString = location.substring(prefix.length());
        log.info("Created new person with id " + idAsString);
        return Long.parseLong(idAsString);
    }

    public void updatePerson(long id, String name, LocalDate birthday) {
        Person person = new Person(name, birthday);
        person.setId(id);
        Response response = put("person/" + id, person);
        assertThat(response.getStatus()).isEqualTo(202);
    }

    public void deletePerson(long id) {
        Response response = delete("person/" + id);
        assertThat(response.getStatus()).isEqualTo(202);
    }
}
