package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Judge;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JudgeRepository extends CrudRepository<Judge, Integer> {
    Judge findByEmail(String username);

    @Query(value = "select j from Judge j")
    List<Judge> getAllJudges();
}
