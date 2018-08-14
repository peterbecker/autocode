package com.github.peterbecker.jpa;

import com.github.peterbecker.autocode.Person;
import com.github.peterbecker.autocode.PersonDao;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@SuppressWarnings("deprecation")
public class PersonTest {
    @Test
    public void testSaveAndLoad() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test");
        EntityManager em = factory.createEntityManager();
        PersonDao dao = new PersonDao(em);

        Person newPerson = new Person("Someone", new Date(1990,10,10));
        dao.save(newPerson);
        long newPersonId = newPerson.getId();

        Person samePerson = dao.load(newPersonId);
        assertThat(samePerson.getName()).isEqualTo(newPerson.getName());
        assertThat(samePerson.getBirthDate()).isEqualTo(newPerson.getBirthDate());
    }

    @Test
    public void testGetters() {
        Person person = new Person("Name", new Date(1919,9,19));
        assertThat(person.getName()).isEqualTo("Name");
        assertThat(person.getBirthDate()).isEqualToIgnoringHours(new Date(1919,9,19));
    }
}
