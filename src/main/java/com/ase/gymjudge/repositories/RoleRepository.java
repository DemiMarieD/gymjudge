package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);

    @Query(value = "select r from Role r")
    List<Role> getAllRoles();
}
