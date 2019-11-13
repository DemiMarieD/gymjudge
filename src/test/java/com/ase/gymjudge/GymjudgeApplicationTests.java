package com.ase.gymjudge;

import com.ase.gymjudge.entities.PersonDEMO;
import com.ase.gymjudge.repositories.PersonRepositoryDEMO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

// @SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
class GymjudgeApplicationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepositoryDEMO personRepositoryDEMO;

    @Test
    void contextLoads() {
        PersonDEMO person = new PersonDEMO("Frank", "Reich");

        entityManager.persist(person);
        entityManager.flush();

        PersonDEMO personFromDB = personRepositoryDEMO.findByFirstName(person.getFirstName());

        assertThat(personFromDB.getFirstName()).isEqualTo(person.getFirstName());
    }

}
