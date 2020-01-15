package com.ase.gymjudge;


import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Status;
import com.ase.gymjudge.entities.Type;
import com.ase.gymjudge.repositories.CompetitionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class GymjudgeApplicationTests {

    //@DataJpaTest
    //public class CompetitionRepositoryTest {
        @Autowired
        private TestEntityManager entityManager;

        @Autowired
        private CompetitionRepository competitionRepository;

        @Test
        public void dbTest() {
            Competition competition = new Competition();
            competition.setAdminID(0);
            competition.setType(Type.TURN10);
            competition.setName("TestComp");
            competition.setStatus(Status.CREATED);

            try {
                competition.setStartDate("2015-05-23 00:00:00");
                competition.setEndDate("2015-05-23 00:00:00");
            } catch (ParseException e) {
                System.out.println(e);
            }

            entityManager.persist(competition);
            entityManager.flush();

            //Competition foundComp = competitionRepository.findById(0)
                    //.orElseThrow(() -> new IllegalArgumentException("Comp Id not found"));

            Competition foundComp = competitionRepository.findAll().iterator().next();

            assertThat(competition.getId()).isEqualTo(foundComp.getId());
            assertThat(competition.getAdminID()).isEqualTo(foundComp.getAdminID());
        }
    //}
}
