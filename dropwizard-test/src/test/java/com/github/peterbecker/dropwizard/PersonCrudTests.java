package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.Person;
import com.github.peterbecker.autocode.Role;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PersonCrudTests extends TestBase {
    @Test
    public void crudCycle() {
        var name = "John Doe";
        var birthday = LocalDate.of(1999, 11, 11);
        var role1 = client.createRole("User", "Your average person");
        var role2 = client.createRole("Admin", "God mode engaged");

        var originalPerson = client.createPerson(name, birthday, role1, role2);
        var id = originalPerson.getId();
        var person = client.getPerson(id);
        assertThat(person.getId()).isEqualTo(id);
        assertThat(person.getName()).isEqualTo(name);
        assertThat(person.getBirthDate()).isEqualTo(birthday);
        assertThat(person.getRoles()).map(Role::getId).containsExactlyInAnyOrder(role1.getId(), role2.getId());

        var newName = "Jane Doe";
        var newBirthday = LocalDate.of(1997, 10, 10);
        var newRole = client.createRole("Auditor", "The watching eye");
        client.updatePerson(id, newName, newBirthday, role1, newRole);
        person = client.getPerson(id);
        assertThat(person.getName()).isEqualTo(newName);
        assertThat(person.getBirthDate()).isEqualTo(newBirthday);
        assertThat(person.getRoles()).map(Role::getId).containsExactlyInAnyOrder(role1.getId(), newRole.getId());

        client.deletePerson(id);
        assertThatThrownBy(() -> client.getPerson(id));
    }

    @Test
    public void getAll() {
        client.createPerson("John Doe", LocalDate.of(1999, 11, 11));
        client.createPerson("Jane Doe", LocalDate.of(2000, 11, 11));
        client.createPerson("Jack Doe", LocalDate.of(2001, 11, 11));

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
        var person = client.createPerson("John Doe", LocalDate.of(1999, 11, 11));
        var id = person.getId();
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
        var person = client.createPerson("John Doe", LocalDate.of(1999, 11, 11));
        var id = person.getId();
        Response response = client.delete("person/" + (id + 1));
        assertThat(response.getStatus()).isEqualTo(404);
        response = client.delete("person/" + id);
        assertThat(response.getStatus()).isEqualTo(202);
        response = client.delete("person/" + id);
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    @Disabled("Not yet implemented")
    public void setUnknownRole() {
        var name = "John Doe";
        var birthday = LocalDate.of(1999, 11, 11);
        // role below is not registered via the client
        var role1 = new Role("User", "Your average person");
        var person = new Person(name, birthday, Set.of(role1));
        var response = client.post("person", person);
        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    @Disabled("Not yet implemented")
    public void setDeletedRole() {
        var name = "John Doe";
        var birthday = LocalDate.of(1999, 11, 11);
        // role below is not registered via the client
        var role1 = client.createRole("User", "Your average person");
        client.deleteRole(role1.getId());
        var person = new Person(name, birthday, Set.of(role1));
        var response = client.post("person", person);
        assertThat(response.getStatus()).isEqualTo(400);
    }
}
