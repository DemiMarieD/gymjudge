package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Bracket;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Bracket, Integer> {
}
