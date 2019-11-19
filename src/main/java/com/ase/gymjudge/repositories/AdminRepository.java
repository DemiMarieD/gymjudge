package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    @Query(value = "select a from Admin a where a.login like ?1")
    List<Admin> existsAdminByUsername(String username);

    @Query(value = "select a.password from Admin a where a.login like ?1")
    String getPasswordFromUser(String username);

    @Query(value = "select a.id from Admin a where a.login like ?1")
    Integer getIdFromUser(String username);



   /* @Query(value = "select s from Article s where s.author like ?1 and s.title = ?2")
    List<Article> findByAuthorAndTitle(String author, String title);*/
}
