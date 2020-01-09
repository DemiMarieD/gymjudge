package com.ase.gymjudge.services;


import com.ase.gymjudge.entities.Role;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.RoleRepository;
import com.ase.gymjudge.repositories.UserRepository;
import com.ase.gymjudge.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        //user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setRoles(userRole);
        userRepository.save(user);
    }

    @Override
    public void saveJudge(User judge) {
        //password is not hashed so it can be made visible
        judge.setPassword(bCryptPasswordEncoder.encode(judge.getPassword()));
        Role judgeRole = roleRepository.findByRole("JUDGE");
       // judge.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        judge.setRoles(judgeRole);
        userRepository.save(judge);
    }


}