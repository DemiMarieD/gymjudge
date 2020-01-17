package com.ase.gymjudge.repositoryTesting;

import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    // write test cases here
    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User alex = new User();
        alex.setFirstname("alex");
        alex.setEmail("alex@web.at");
        entityManager.persist(alex);
        entityManager.flush();

        // when
        User found = userRepository.findByEmail(alex.getEmail());

        // then
        assertThat(found.getFirstname())
                .isEqualTo(alex.getFirstname());
    }

}
