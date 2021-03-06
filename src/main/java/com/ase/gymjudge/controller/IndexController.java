package com.ase.gymjudge.controller;
import javax.validation.Valid;

import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Role;
import com.ase.gymjudge.entities.Status;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.RoleRepository;
import com.ase.gymjudge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private CompetitionRepository compRepository;

    @Autowired
    private RoleRepository roleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        boolean noAdmin = true;
        boolean noJudge = true;
        for (Role r: roleRepository.getAllRoles()){
            if(r.getRole().equals("ADMIN")){
                noAdmin = false;
            }
            else if(r.getRole().equals("JUDGE")){
                noJudge = false;
            }
        }

        if (noAdmin) {
            Role adminRole = new Role();
            adminRole.setRole("ADMIN");
            roleRepository.save(adminRole);
        }
        if(noJudge){
            Role judgeRole = new Role();
            judgeRole.setRole("JUDGE");
            roleRepository.save(judgeRole);
        }

    }

    @RequestMapping(value= {"/", "/index"}, method=RequestMethod.GET)
    public ModelAndView login(ModelAndView model) {
        List<Competition> competitions = compRepository.getCompetitionsByStatus(Status.ACTIVE);
        model.addObject("competitions", competitions);
        model.setViewName("/index");
        return model;
    }

}
