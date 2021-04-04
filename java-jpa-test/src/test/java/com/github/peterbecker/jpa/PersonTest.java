package com.github.peterbecker.jpa;

import com.github.peterbecker.autocode.Person;
import com.github.peterbecker.autocode.PersonDao;
import com.github.peterbecker.autocode.Role;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class PersonTest {

    public static final Set<Role> TEST_ROLES =
            Set.of(new Role("role1", "first"), new Role("role2", "second"));

    @Test
    public void testSaveAndLoad() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test");
        EntityManager em = factory.createEntityManager();
        PersonDao dao = new PersonDao(em);

        Person newPerson = new Person("Someone", LocalDate.of(1990, 11, 10), TEST_ROLES);
        dao.save(newPerson);
        long newPersonId = newPerson.getId();

        Person samePerson = dao.load(newPersonId);
        assertThat(samePerson.getName()).isEqualTo(newPerson.getName());
        assertThat(samePerson.getBirthDate()).isEqualTo(newPerson.getBirthDate());
        assertThat(samePerson.getRoles()).isEqualTo(newPerson.getRoles());
    }

    @Test
    public void testGetters() {
        Person person = new Person("Name", LocalDate.of(1919, 10, 19), TEST_ROLES);
        assertThat(person.getName()).isEqualTo("Name");
        assertThat(person.getBirthDate()).isEqualTo(LocalDate.of(1919, 10, 19));
        assertThat(person.getRoles()).containsExactlyElementsOf(TEST_ROLES);
    }
}
