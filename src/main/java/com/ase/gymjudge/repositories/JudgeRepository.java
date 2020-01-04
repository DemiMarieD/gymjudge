package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Judge;
import com.ase.gymjudge.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface JudgeRepository extends CrudRepository<Judge, Integer> {


    /*@Query(value = "SELECT u.email FROM USER u INNER JOIN user_role ur ON (u.id = ur.user_id) INNER JOIN role r ON (ur.role_id=r.role_id) WHERE r.role = 'JUDGE'")
    List<User> judgesEmails();*/

    @Query(value = "select j from Judge j")
    List<Judge> getAllJudges();

    @Query(value = "delete from Judge j where j.judgeID like ?1")
    Judge getJudgeByJudgeID(int judgeID);
}
//