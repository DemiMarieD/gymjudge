package com.ase.gymjudge.services;

import com.ase.gymjudge.entities.Judge;
import com.ase.gymjudge.entities.Role;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.JudgeRepository;
import com.ase.gymjudge.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

public class JudgeServiceImpl implements JudgeService{
    @Autowired
    private JudgeRepository judgeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Judge findByLogin(String login) {
        return judgeRepository.findByLogin(login);
    }

    @Override
    public void save(Judge judge) {
        judge.setPassword(judge.getPassword());
        Role role = roleRepository.findByRole("JUDGE");
        judge.setRole(role);
        judgeRepository.save(judge);
    }
}
