package com.ase.gymjudge;

import com.ase.gymjudge.entities.PersonDEMO;
import com.ase.gymjudge.repositories.PersonDEMORepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class GymjudgeApplicationTests {

    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class PersonDEMORepositoryIntegrationTest {

        @Autowired
        private TestEntityManager entityManager;

        @Autowired
        private PersonDEMORepository personDEMORepository;

        // write test cases here
        @Test
        void contextLoads() {
            PersonDEMO person = new PersonDEMO("Frank", "Reich");

            entityManager.persist(person);
            entityManager.flush();

            PersonDEMO personFromDB = personDEMORepository.findByFirstName(person.getFirstName());

            assertThat(personFromDB.getFirstName()).isEqualTo(person.getFirstName());
        }

    }

}
