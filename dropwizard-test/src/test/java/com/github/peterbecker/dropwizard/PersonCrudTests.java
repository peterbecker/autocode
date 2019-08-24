package com.github.peterbecker.dropwizard;

import com.github.peterbecker.autocode.Person;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Ignore("not yet implemented")
public class PersonCrudTests extends TestBase {
    @Test
    public void crudCycle() {
        TestClient client = createClient();

        String name = "John Doe";
        LocalDate bDay = LocalDate.of(1999, 11, 11);

        long id = client.addPerson(name, bDay);
        Person person = client.getPerson(id);
        assertThat(person.getName()).isEqualTo(name);
        assertThat(person.getBirthDate()).isEqualTo(bDay);

        String newName = "Jane Doe";
        LocalDate newBday = LocalDate.of(1997,10,10);
        client.updatePerson(id, newName, newBday);
        person = client.getPerson(id);
        assertThat(person.getName()).isEqualTo(name);
        assertThat(person.getBirthDate()).isEqualTo(bDay);

        client.deletePerson(id);
        assertThatThrownBy(() -> client.getPerson(id));
    }
}
