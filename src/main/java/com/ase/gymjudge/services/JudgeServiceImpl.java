package com.ase.gymjudge.services;

import com.ase.gymjudge.entities.Judge;
import com.ase.gymjudge.entities.Role;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.JudgeRepository;
import com.ase.gymjudge.repositories.RoleRepository;
import com.ase.gymjudge.repositories.UserRepository;
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

 @Override
 public Judge findByEmail(String email) {

     return judgeRepository.findByEmail(email);
 }

 @Override
 public void save(Judge judge) {
     Role  judgeRole = roleRepository.findByRole("JUDGE");
     judge.setRoles(new HashSet<>(Arrays.asList(judgeRole)));
     judgeRepository.save(judge);
 }
 @Override
  public void delete(Integer judgeID){
    judgeRepository.delete(judgeID);
 }



    }



