package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Grouping;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Grouping, Integer> {
}
