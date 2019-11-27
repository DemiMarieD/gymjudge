package com.ase.gymjudge.controller;
//import org.springframework.http.ResponseEntity;
import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CompetitionController {
    @Autowired
    private CompetitionRepository compRepository;
    @Autowired
    private UserService userService;

    //COMPETITIONS
    @RequestMapping(value = { "home/addcompetition" }, method = RequestMethod.GET)
    public ModelAndView createNewCompetition(ModelAndView model) {
        Competition competition = new Competition();
       // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      //  User user = userService.findByEmail(auth.getName());
        model.addObject("competition", competition);
       // model.addObject("adminId", user.getId());
        model.setViewName ("home/addcompetition");
        return model;
    }
    @PostMapping("home/addcompetition")
    public String addCompetition(@Valid Competition competition, BindingResult result, Model model) {
      //todo: check why using ModelAndView is causing errors in the Post Mapping...
        if (result.hasErrors()) {
           // model.setViewName ("home/addcompetition");
            return "home/addcompetition";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        competition.setAdminID(user.getId());
        compRepository.save(competition);

       /* model.addObject("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.addObject("adminId", user.getId());*/
        //model.setViewName ("home/competitions");
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.addAttribute("adminId", user.getId());
        return "home/competitions";
    }

    @GetMapping("home/competitions")
    public ModelAndView showCompetitions(Competition competition, ModelAndView model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addObject("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.setViewName("home/competitions");
        return model;
    }

    @GetMapping("/update-competitions")
    public String updateComps(Competition comps, Model model) {
        model.addAttribute("competitions", compRepository.findAll());
        return "home/competitions :: #table";
    }

}
