package com.ase.gymjudge.services;

import com.ase.gymjudge.entities.Judge;

public interface JudgeService {
    public Judge findByLogin(String login);
    public void save(Judge judge);
}
