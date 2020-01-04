package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Judge;
import com.ase.gymjudge.entities.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompetitionRepository extends CrudRepository<Competition, Integer> {
    @Query(value = "select c from Competition c where c.adminID like ?1")
    List<Competition> getCompetitionsByUserId(Integer userId);

    @Query(value = "select c from Competition c where c.status like ?1")
    List<Competition> getCompetitionsByStatus(Status status);

    @Query(value = "select c.categories from Competition c where c.status like ?1")
    List<Category> getCategories(Integer competitionId);

    @Query(value = "select c.judges from Competition c where c.type like ?1")
    List<Judge> getJudges(Integer competitionId);
}

//