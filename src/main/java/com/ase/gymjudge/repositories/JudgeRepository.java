package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Judge;
import com.ase.gymjudge.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface JudgeRepository extends CrudRepository<User, Integer> {


    /*@Query(value = "SELECT u.email FROM USER u INNER JOIN user_role ur ON (u.id = ur.user_id) INNER JOIN role r ON (ur.role_id=r.role_id) WHERE r.role = 'JUDGE'")
    User judgesEmails();*/
    User findByEmail(String email);

    /*@Query(value = "select j from user j inner join user_role ur on (u.id = ur.user_id) inner join role r on (ur.role_id=r.role_id) where  r.role = 'JUDGE'")
    List<User> getAllJudges();*/

}
//