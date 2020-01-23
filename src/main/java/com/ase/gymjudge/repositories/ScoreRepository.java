package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.HashMap;
import java.util.List;

public interface ScoreRepository extends CrudRepository<Score, Integer> {
    @Query(value = "select s from Score s, Participants p, Competition c where c.id = ?1 and p.id = s.participants.id order by s.date desc")
    List<Score> getScoresByCompetitionId(int competitionId);

    @Query(value = "select s from Score s, Participants p, Competition c where c.id = ?1 and p.id = s.participants.id and s.status = 1 order by s.date desc")
    List<Score> getActiveScoresByCompetitionId(int competitionId);

    @Query(value = "select s from Score s where s.participants.id = ?1 and s.status = 1 order by s.date desc")
    List<Score> getActiveScoresByParticipantId(int participantId);
}