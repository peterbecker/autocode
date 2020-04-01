package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.Person;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Ignore("Database migrations not yet implemented (code may or may not work)")
public class PersonCrudTests extends TestBase {
    @Test
    public void crudCycle() {
        var client = createClient();

        var name = "John Doe";
        var birthday = LocalDate.of(1999, 11, 11);

        var id = client.addPerson(name, birthday);
        var person = client.getPerson(id);
        assertThat(person.getName()).isEqualTo(name);
        assertThat(person.getBirthDate()).isEqualTo(birthday);

        var newName = "Jane Doe";
        var newBirthday = LocalDate.of(1997,10,10);
        client.updatePerson(id, newName, newBirthday);
        person = client.getPerson(id);
        assertThat(person.getName()).isEqualTo(newName);
        assertThat(person.getBirthDate()).isEqualTo(newBirthday);

        client.deletePerson(id);
        assertThatThrownBy(() -> client.getPerson(id));
    }
}
