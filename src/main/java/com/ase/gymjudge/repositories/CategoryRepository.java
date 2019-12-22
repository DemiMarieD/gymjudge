package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Participants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository  extends CrudRepository<Category, Integer> {
    @Query(value = "select c from Category c")
    List<Category> getAllCategories();

    //@Query(value = "select p from Category c, Participants p where c.id like ?1 and c like p.category")
    @Query(value = "select c.participants from Category c where c.id like ?1")
    List<Participants> getParticipants(int categoryId);

    @Query(value = "select c.competition from Category c where c.id like ?1")
    Competition getCompetition(int categoryId);


}
