package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.Person;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PersonCrudTests extends TestBase {
    @Test
    public void crudCycle() {
        var name = "John Doe";
        var birthday = LocalDate.of(1999, 11, 11);

        var id = client.addPerson(name, birthday);
        var person = client.getPerson(id);
        assertThat(person.getId()).isEqualTo(id);
        assertThat(person.getName()).isEqualTo(name);
        assertThat(person.getBirthDate()).isEqualTo(birthday);

        var newName = "Jane Doe";
        var newBirthday = LocalDate.of(1997, 10, 10);
        client.updatePerson(id, newName, newBirthday);
        person = client.getPerson(id);
        assertThat(person.getName()).isEqualTo(newName);
        assertThat(person.getBirthDate()).isEqualTo(newBirthday);

        client.deletePerson(id);
        assertThatThrownBy(() -> client.getPerson(id));
    }

    @Test
    public void getAll() {
        client.addPerson("John Doe", LocalDate.of(1999, 11, 11));
        client.addPerson("Jane Doe", LocalDate.of(2000, 11, 11));
        client.addPerson("Jack Doe", LocalDate.of(2001, 11, 11));

        var result = client.getAllPersons();
        assertThat(result.stream().map(Person::getName))
                .contains(
                        "John Doe", "Jane Doe", "Jack Doe"
                );
        assertThat(result.stream().map(Person::getBirthDate))
                .allMatch(
                        d -> d.getMonth().equals(Month.NOVEMBER) && d.getDayOfMonth() == 11
                );
    }

    @Test
    public void get404s() {
        var id = client.addPerson("John Doe", LocalDate.of(1999, 11, 11));
        Response response = client.get("person/" + (id + 1));
        assertThat(response.getStatus()).isEqualTo(404);
        response = client.get("person/" + id);
        assertThat(response.getStatus()).isEqualTo(200);
        client.delete("person/" + id);
        response = client.get("person/" + id);
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void delete404s() {
        var id = client.addPerson("John Doe", LocalDate.of(1999, 11, 11));
        Response response = client.delete("person/" + (id + 1));
        assertThat(response.getStatus()).isEqualTo(404);
        response = client.delete("person/" + id);
        assertThat(response.getStatus()).isEqualTo(202);
        response = client.delete("person/" + id);
        assertThat(response.getStatus()).isEqualTo(404);
    }
}
