package com.ase.gymjudge.services;

import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.User;

import java.util.List;

public interface JudgeService {
    public User findByEmail(String email);
    public void save(User judge);
    public void delete(User judge);
    public List<User> create(Competition comp);
}
//