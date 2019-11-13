package com.ase.gymjudge.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ase.gymjudge.entities.PersonDEMO;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepositoryDemo extends CrudRepository<PersonDEMO, Integer> {

}
