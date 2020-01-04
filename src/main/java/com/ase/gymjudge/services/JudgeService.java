package com.ase.gymjudge.services;

import com.ase.gymjudge.entities.Judge;

public interface JudgeService {
    //public Judge findByEmail(String email);
    public void save(Judge judge);
    public void delete(Judge judge);
}
//