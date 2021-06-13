package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.Person;
import com.github.peterbecker.autocode.Role;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
        var response = get("person");
        assertThat(response.getStatus()).isEqualTo(200);
        return response.readEntity(new GenericType<>(){});
    }

    public Person getPerson(long id) {
        var response = get("person/" + id);
        assertThat(response.getStatus()).isEqualTo(200);
        return response.readEntity(Person.class);
    }

    public Person createPerson(String name, LocalDate birthday, Role... roles) {
        var person = new Person(name, birthday, Set.of(roles));
        var response = post("person", person);
        assertThat(response.getStatus()).isEqualTo(201);
        var prefix = baseUrl + "person/";
        var location = response.getHeaderString("location");
        assertThat(location).startsWith(prefix);
        var idAsString = location.substring(prefix.length());
        log.info("Created new person with id " + idAsString);
        person.setId(Long.parseLong(idAsString));
        return person;
    }

    public void updatePerson(long id, String name, LocalDate birthday, Role... roles) {
        var person = new Person(name, birthday, Set.of(roles));
        person.setId(id);
        var response = put("person/" + id, person);
        assertThat(response.getStatus()).isEqualTo(202);
    }

    public void deletePerson(long id) {
        var response = delete("person/" + id);
        assertThat(response.getStatus()).isEqualTo(202);
    }

    public Role createRole(String name, String description) {
        var role = new Role(name, description);
        var response = post("role", role);
        assertThat(response.getStatus()).isEqualTo(201);
        var prefix = baseUrl + "role/";
        var location = response.getHeaderString("location");
        assertThat(location).startsWith(prefix);
        var idAsString = location.substring(prefix.length());
        log.info("Created new role with id " + idAsString);
        role.setId(Long.parseLong(idAsString));
        return role;
    }

    public void deleteRole(long id) {
        var response = delete("role/" + id);
        assertThat(response.getStatus()).isEqualTo(202);
    }
}
