package com.ase.gymjudge.repositoryTesting;

import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompetitionRepositoryIntegrationTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetitionRepository compRepository;

    @Test
    public void whenFindByEmail_thenReturnUser() {



    }

}
