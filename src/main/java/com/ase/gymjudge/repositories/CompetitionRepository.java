package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Competition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition, Integer> {
    @Query(value = "select c from Competition c where c.adminID like ?1")
    List<Competition> getCompetitionsByUserId(Integer userId);
}

