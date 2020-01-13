package com.ase.gymjudge.services;

import com.ase.gymjudge.entities.User;

public interface UserService {
    public User findByEmail(String email);
    public void save(User user);
    public void saveJudge(User judge);
    public void deleteJudge(User judge);
}