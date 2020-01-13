package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Apparatus;
import com.ase.gymjudge.entities.Grouping;
import com.ase.gymjudge.entities.Participants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Grouping, Integer> {
    @Query(value = "select b from Grouping b")
    List<Grouping> getAllGroups();

    @Query(value = "select b from Grouping b where b.id = ?1")
    List<Grouping> getGroupById(Integer groupId);

}
