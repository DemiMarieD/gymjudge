package com.ase.gymjudge.repositories;

import com.ase.gymjudge.entities.PersonDEMO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDEMORepository extends JpaRepository<PersonDEMO, Integer> {
    public PersonDEMO findByFirstName(String firstName);
}
