package com.ase.gymjudge.services;

import com.ase.gymjudge.entities.Judge;
import com.ase.gymjudge.entities.Role;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.JudgeRepository;
import com.ase.gymjudge.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class JudgeServiceImpl implements JudgeService{
    @Autowired
    private JudgeRepository judgeRepository;
    @Autowired
    private RoleRepository roleRepository;

    /*@Override
    public Judge findByEmail(String email) {
        return judgeRepository.findByLogin(login);
    }*/

    @Override
    public void save(Judge judge) {
        Role role = roleRepository.findByRole("JUDGE");
        judge.setRole(role);
        judgeRepository.save(judge);
    }
    @Override
    public void delete(Judge judge){
        judgeRepository.deleteById(judge.getJudgeID());
    }
}
