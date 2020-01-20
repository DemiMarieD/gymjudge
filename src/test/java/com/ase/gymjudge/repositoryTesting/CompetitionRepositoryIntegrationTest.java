package com.ase.gymjudge.repositoryTesting;

import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Status;
import com.ase.gymjudge.entities.Type;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompetitionRepositoryIntegrationTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetitionRepository compRepository;

    private Competition comp = new Competition();
    private Competition comp2 = new Competition();
    private Competition comp3 = new Competition();

    @Before
    public void makeCompetition () throws ParseException {
        comp.setName("NewComp");
        comp.setAdminID(1);
        comp.setStatus(Status.ACTIVE);
        comp.setStartDate("01-02-2020");
        comp.setEndDate("02-02-2020");
        comp.setType(Type.TURN10);
        entityManager.persist(comp);

        comp2.setName("NewComp2");
        comp2.setStatus(Status.ACTIVE);
        comp2.setAdminID(2);
        comp2.setStartDate("01-02-2020");
        comp2.setEndDate("02-02-2020");
        comp2.setType(Type.TURN10);
        entityManager.persist(comp2);

        comp3.setName("NewComp3");
        comp3.setAdminID(2);
        comp3.setStatus(Status.CREATED);
        comp3.setStartDate("01-02-2020");
        comp3.setEndDate("02-02-2020");
        comp3.setType(Type.TURN10);
        entityManager.persist(comp3);
        entityManager.flush();
    }

    @Test
    public void testGetCompByID() {
        Competition found = compRepository.getCompetitionsById(comp.getId());
        assertThat(found.getName())
                .isEqualTo(comp.getName());
    }

    @Test
    public void testGetCompByUserId_returnOne() {
        List<Competition> found = compRepository.getCompetitionsByUserId(1);
        for(Competition c : found) {
            assertThat(c.getName())
                    .isEqualTo(comp.getName());
        }
    }

    @Test
    public void testGetCompByUserId_returnMultiple() {
        List<Competition> found = compRepository.getCompetitionsByUserId(2);
        assertThat(found.size())
                .isEqualTo(2);
    }

    @Test
    public void testGetCompByStatus_returnOne() {
        List<Competition> found = compRepository.getCompetitionsByStatus(Status.CREATED);
        for(Competition c : found) {
            assertThat(c.getName())
                    .isEqualTo(comp3.getName());
        }
    }

    @Test
    public void testGetCompByStatus_returnMultiple() {
        List<Competition> found = compRepository.getCompetitionsByStatus(Status.ACTIVE);
        assertThat(found.size())
                .isEqualTo(2);
    }
}
