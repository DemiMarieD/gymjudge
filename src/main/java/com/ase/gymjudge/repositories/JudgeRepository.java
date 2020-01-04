package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Judge;
import com.ase.gymjudge.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JudgeRepository extends CrudRepository<Judge, Integer> {

   // Judge findByEmail(String email);

    @Query(value = "select j from Judge j")
    List<Judge> getAllJudges();

    @Query(value = "delete from Judge j where j.judgeID like ?1")
    Judge getJudgeByJudgeID(int judgeID);
}
//