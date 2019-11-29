package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Judge;
import org.springframework.data.repository.CrudRepository;

public interface JudgeRepository extends CrudRepository<Judge, Integer> {
}
