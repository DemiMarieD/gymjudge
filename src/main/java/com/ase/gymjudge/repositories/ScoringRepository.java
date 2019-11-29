package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Scoring;
import org.springframework.data.repository.CrudRepository;

public interface ScoringRepository extends CrudRepository<Scoring, Integer> {
}
