package com.ase.gymjudge.entityTesting;


import com.ase.gymjudge.entities.*;
import com.ase.gymjudge.repositories.CompetitionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
public class GymjudgeApplicationIT {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetitionRepository competitionRepository;

    Competition competition;
    List<Category> categories;
    List<Participants> participants;
    List<Grouping> groups;
    List<User> judges;
    Participants participant;
    User judge;
    Category ak14;
    Category ak16;
    Grouping groupA;
    Grouping groupB;
    Role judgeRoll;
    Score score;

    @Before
    public void setUpCompetition() {
        competition = createCompetition();
        categories = new ArrayList<>();
        participants = new ArrayList<>();
        groups = new ArrayList<>();
        judges = new ArrayList<>();
        participant = new Participants();
        judge = new User();
        ak14 = new Category();
        ak16 = new Category();
        groupA = new Grouping();
        groupB = new Grouping();
        judgeRoll = new Role();
        score = new Score();

        // Role
        judgeRoll.setId(7);
        judgeRoll.setRole("JUDGE");

        // Judges (also set optional Admin fields)
        judge.setCompetition(competition);
        judge.setActive(1);
        judge.setId(6);
        judge.setRoles(judgeRoll);
        judge.setApparatus(Apparatus.BODEN);
        judge.setEmail("test0@gymjudge.at");

        judge.setClub("Test");
        judge.setFirstname("Emma");
        judge.setLastname("Emmason");
        judge.setPassword("password");

        judges.add(judge);

        // Participants
        participant.setCompetition(competition);
        participant.setFirstname("Max");
        participant.setLastname("Mustermann");
        participant.setId(8);
        participant.setClub("Test");
        participant.setGender(Gender.MALE);
        try {
            participant.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse("20/11/2010"));
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        participants.add(participant);

        // Groups
        groupA.setCompetition(competition);
        groupB.setCompetition(competition);
        groupA.setName("Group A");
        groupB.setName("Group B");
        groupA.setApparatuses(createTurn10Apparatuses().subList(0,4));
        groupB.setApparatuses(createTurn10Apparatuses().subList(1,5));
        groupA.setId(2);
        groupB.setId(3);
        groups.add(groupA);
        groups.add(groupB);
        groupA.setParticipants(participants);

        // Categories
        ak14.setCompetition(competition);
        ak16.setCompetition(competition);
        ak14.setId(4);
        ak16.setId(5);
        ak14.setLabel("AK 14");
        ak16.setLabel("AK 16");
        ak14.setDescription("AK 14, Unterstufe, m");
        ak16.setDescription("AK 16, Oberstufe, m");
        ak14.setParticipants(participants);
        categories.add(ak14);
        categories.add(ak16);

        participant.setCategory(ak14);
        participant.setGrouping(groupA);

        // Competition
        competition.setId(1);
        competition.setCategories(categories);
        competition.setGroups(groups);
        competition.setParticipants(participants);
        competition.setJudgePassword("");
        competition.setJudges(judges);

        // Score
        score.setId(9);
        score.setStatus(1);
        score.setApparatus(Apparatus.BODEN);
        score.setD(0);
        score.setE1(0);
        score.setE2(0);
        score.setE3(0);
        score.setE4(0);
        score.setN(0);
        score.setParticipants(participant);
    }

    @Test
    public void dbTest() {
        competitionRepository.deleteAll();

        Competition competition = createCompetition();

        entityManager.persist(competition);
        entityManager.flush();

        Competition foundComp = competitionRepository.findAll().iterator().next();

        assertThat(competition.getId()).isEqualTo(foundComp.getId());
        assertThat(competition.getAdminID()).isEqualTo(foundComp.getAdminID());
        assertThat(competition.getName()).isEqualTo(foundComp.getName());
    }

    @Test
    public void competitionTest() {
        assertThat(competition.getGroups()).isEqualTo(groups);

        // Test if ApparatusList (shown in Dropdowns,...) is correct depending on Competition Type
        competition.setType(Type.STUFENWETTKAMPF);
        assertThat(competition.getAvailableApparatuses()).isEqualTo(createStufeApparatuses());
        competition.setType(Type.TURN10);
        assertThat(competition.getAvailableApparatuses()).isEqualTo(createTurn10Apparatuses());

        // Test if ordered Groups for Judge "Boden" really gets groups which are starting in the correct round
        assertThat(competition.getGroupingOrderFor(Apparatus.PFERD).get(0).getApparatuses().get(0))
                .isEqualTo(Apparatus.PFERD);
        assertThat(competition.getGroupingOrderFor(Apparatus.PFERD).get(1).getApparatuses().get(1))
                .isEqualTo(Apparatus.PFERD);

        // Test if returned list is always greater or equal to every requested list
        assertThat(competition.getGroupingOrderFor(Apparatus.PFERD).size())
                .isGreaterThanOrEqualTo(groupA.getApparatuses().size());
        assertThat(competition.getGroupingOrderFor(Apparatus.PFERD).size())
                .isGreaterThanOrEqualTo(groupB.getApparatuses().size());
    }

    @Test
    public void testCompetitionGetters() {
        assertThat(competition.getId()).isEqualTo(1);
        assertThat(competition.getName()).isEqualTo("TestComp");
        assertThat(competition.getAdminID()).isEqualTo(0);
        assertThat(competition.getDescription()).isEqualTo("TestDescription");
        assertThat(competition.getJudgePassword()).isEqualTo("");
        assertThat(competition.getStartDate().toString()).isEqualTo("Wed Jan 23 00:05:00 CET 2019");
        assertThat(competition.getEndDate().toString()).isEqualTo("Wed Jan 23 00:05:00 CET 2019");
        assertThat(competition.getType()).isEqualTo(Type.TURN10);
        assertThat(competition.getStatus()).isEqualTo(Status.CREATED);
        assertThat(competition.getParticipants()).isEqualTo(participants);
        assertThat(competition.getCategories()).isEqualTo(categories);
        assertThat(competition.getGroups()).isEqualTo(groups);
        assertThat(competition.getJudges()).isEqualTo(judges);
    }

    @Test
    public void testCategoryGetters() {
        assertThat(ak14.getCompetition()).isEqualTo(competition);
        assertThat(ak14.getDescription()).isEqualTo("AK 14, Unterstufe, m");
        assertThat(ak14.getId()).isEqualTo(4);
        assertThat(ak14.getLabel()).isEqualTo("AK 14");
        assertThat(ak14.getParticipants()).isEqualTo(participants);
    }

    @Test
    public void testGroupingGetters() {
        assertThat(groupA.getId()).isEqualTo(2);
        assertThat(groupA.getApparatuses()).isEqualTo(createTurn10Apparatuses().subList(0,4));
        assertThat(groupA.getCompetition()).isEqualTo(competition);
        assertThat(groupA.getParticipants()).isEqualTo(participants);
        assertThat(groupA.getGroupApparatuses()).isEqualTo(competition.getAvailableApparatuses());
        assertThat(groupA.getName()).isEqualTo("Group A");
    }

    @Test
    public void testParticipantsGetters() {
        assertThat(participant.getId()).isEqualTo(8);
        assertThat(participant.getCompetition()).isEqualTo(competition);
        assertThat(participant.getCategory()).isEqualTo(ak14);
        assertThat(participant.getGender()).isEqualTo(Gender.MALE);
        assertThat(participant.getGrouping()).isEqualTo(groupA);
        assertThat(participant.getClub()).isEqualTo("Test");
        assertThat(participant.getFirstname()).isEqualTo("Max");
        assertThat(participant.getLastname()).isEqualTo("Mustermann");
        assertThat(participant.getBirthday().toString()).isEqualTo("Sat Nov 20 00:00:00 CET 2010");
        assertThat(participant.getAge()).isEqualTo(9);
        assertThat(participant.getParticipantsInfo()).isEqualTo("Max, Mustermann  (9)  male");
    }

    @Test
    public void testRoleGetters() {
        assertThat(judgeRoll.getId()).isEqualTo(7);
        assertThat(judgeRoll.getRole()).isEqualTo("JUDGE");
    }

    @Test
    public void testJudgeGetters() {
        assertThat(judge.getId()).isEqualTo(6);
        assertThat(judge.getEmail()).isEqualTo("test0@gymjudge.at");
        assertThat(judge.getPassword()).isEqualTo("password");
        assertThat(judge.getApparatus()).isEqualTo(Apparatus.BODEN);
        assertThat(judge.getCompetition()).isEqualTo(competition);
        assertThat(judge.getFirstname()).isEqualTo("Emma");
        assertThat(judge.getLastname()).isEqualTo("Emmason");
        assertThat(judge.getRoles()).isEqualTo(judgeRoll);
        assertThat(judge.getActive()).isEqualTo(1);
        assertThat(judge.getClub()).isEqualTo("Test");
    }

    @Test
    public void testScoreGetter() {
        assertThat(score.getId()).isEqualTo(9);
        assertThat(score.getStatus()).isEqualTo(1);
        assertThat(score.getApparatus()).isEqualTo(Apparatus.BODEN);
        assertThat(score.getD()).isEqualTo(0);
        assertThat(score.getE1()).isEqualTo(0);
        assertThat(score.getE2()).isEqualTo(0);
        assertThat(score.getE3()).isEqualTo(0);
        assertThat(score.getE4()).isEqualTo(0);
        assertThat(score.getN()).isEqualTo(0);
        assertThat(score.getParticipants()).isEqualTo(participant);
        assertThat(score.getDate()).isNull();
    }

    @Test
    public void EnumTests() {
        assertThat(Apparatus.BODEN.getDisplayValue()).isEqualTo("Boden");
        assertThat(Gender.FEMALE.getDisplayValue()).isEqualTo("female");
        assertThat(Status.ACTIVE.getDisplayValue()).isEqualTo("Active");
        assertThat(Type.TURN10.getDisplayValue()).isEqualTo("Turn10");
    }

    private Competition createCompetition() {
        Competition competition = new Competition();
        competition.setAdminID(0);
        competition.setType(Type.TURN10);
        competition.setName("TestComp");
        competition.setDescription("TestDescription");
        competition.setStatus(Status.CREATED);

        try {
            competition.setStartDate("2019-05-23 00:00:00");
            competition.setEndDate("2019-05-23 00:00:00");
        } catch (ParseException e) {
            System.out.println(e.toString());
        }

        return competition;
    }

    private List<Apparatus> createTurn10Apparatuses() {
        List<Apparatus> apparatuses = new ArrayList<>();
        apparatuses.add(Apparatus.BODEN);
        apparatuses.add(Apparatus.PFERD);
        apparatuses.add(Apparatus.RINGE);
        apparatuses.add(Apparatus.SPRUNG);
        apparatuses.add(Apparatus.BARREN);
        apparatuses.add(Apparatus.RECK);
        apparatuses.add(Apparatus.BALKEN);
        apparatuses.add(Apparatus.STUFENBARREN);
        apparatuses.add(Apparatus.MINITRAMPOLIN);
        apparatuses.add(Apparatus.PAUSE);

        return apparatuses;
    }

    private List<Apparatus> createStufeApparatuses() {
        List<Apparatus> apparatuses = new ArrayList<>();
        apparatuses.add(Apparatus.STATION1);
        apparatuses.add(Apparatus.STATION2);
        apparatuses.add(Apparatus.STATION3);
        apparatuses.add(Apparatus.STATION4);
        apparatuses.add(Apparatus.STATION5);
        apparatuses.add(Apparatus.STATION6);
        apparatuses.add(Apparatus.STATION7);
        apparatuses.add(Apparatus.STATION8);
        apparatuses.add(Apparatus.STATION9);
        apparatuses.add(Apparatus.STATION10);
        apparatuses.add(Apparatus.PAUSE);

        return apparatuses;
    }
}
