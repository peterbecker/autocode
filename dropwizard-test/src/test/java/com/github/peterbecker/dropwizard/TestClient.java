package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.Person;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SuppressWarnings("WeakerAccess")
public class TestClient {
    private final Client httpClient;
    private final String baseUrl;

    public TestClient(Environment env, int sutPort) {
        httpClient = new JerseyClientBuilder(env).build("test client");
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

    public <T> Response delete(String path) {
        return httpClient.target(baseUrl + path).request().delete();
    }

    public Person getPerson(long id) {
        Response response = get("person/" + id);
        assertThat(response.getStatus()).isEqualTo(200);
        return response.readEntity(Person.class);
    }

    public long addPerson(String name, LocalDate birthday) {
        Person person = new Person(name, java.sql.Date.valueOf(birthday));
        Response response = post("person", person);
        assertThat(response.getStatus()).isEqualTo(201);
        String prefix = baseUrl + "person/";
        assertThat(response.getHeaderString("location")).startsWith(prefix);
        String idAsString = response.getHeaderString("location").substring(prefix.length());
        return Long.parseLong(idAsString);
    }

    public void updatePerson(long id, String name, LocalDate birthday) {
        Person person = new Person(name, java.sql.Date.valueOf(birthday));
        Response response = put("person/" + id, person);
        assertThat(response.getStatus()).isEqualTo(204);
    }

    public void deletePerson(long id) {
        Response response = delete("person/" + id);
        assertThat(response.getStatus()).isEqualTo(204);
    }
}
