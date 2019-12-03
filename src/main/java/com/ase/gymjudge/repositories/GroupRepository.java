package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Bracket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Bracket, Integer> {
    @Query(value = "select b from Bracket b")
    List<Bracket> getAllGroups();
}
