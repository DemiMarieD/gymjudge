package com.ase.gymjudge.services;

import com.ase.gymjudge.entities.*;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.JudgeRepository;
import com.ase.gymjudge.repositories.RoleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Autowired
    private JudgeRepository judgeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


     @Override
     public User findByEmail(String email){
         return judgeRepository.findByEmail(email);
     }

    public List<User> create(Competition comp) {
        List<User> judges = new ArrayList<>();
        Role role = roleRepository.findByRole("JUDGE");
        List<Apparatus> apparatuses =comp.getAvailableApparatuses();
        for (Apparatus app : apparatuses) {
            User judge = new User();
            judge.setFirstname(app.getDisplayValue());
            judge.setLastname(comp.getName());
            judge.setEmail(app.getDisplayValue()+ comp.getId());
            judge.setApparatus(app);
            judge.setPassword(bCryptPasswordEncoder.encode(judge.getPassword()));
            judge.setActive(1);
            judge.setRoles(new HashSet<Role>(Arrays.asList(role)));
            judges.add(judge);
        }

        return judges;
    }

    @Override
    public void save(User judge) {
        judgeRepository.save(judge);
    }

    @Override
    public void delete(User judge) {
        judgeRepository.delete(judge);
    }
}
//