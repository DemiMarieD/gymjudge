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

    public User getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        return user;
    }

    @RequestMapping(value = { "home/competitions/new" }, method = RequestMethod.GET)
    public ModelAndView createNewCompetition(ModelAndView model) {
        Competition competition = new Competition();

        model.addObject("competition", competition);
        model.setViewName ("home/competitions/new");
        return model;
    }
    @PostMapping("home/competitions/new")
    public String addCompetition(@Valid Competition competition, BindingResult result, Model model) {
      //todo: check why using ModelAndView is causing errors in the Post Mapping...
        if (result.hasErrors()) {
           // model.setViewName ("home/addcompetition");
            return "home/competitions/new";
        }
        User user = getLoggedInUser();
        //todo: check DATES
        competition.setAdminID(user.getId());
        //todo: check TYPE (if-else) and create # judge login
        compRepository.save(competition);

       /* model.addObject("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.addObject("adminId", user.getId());*/
        //model.setViewName ("home/competitions");
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.addAttribute("adminId", user.getId());
        return "redirect:/home/home";
    }

    //should not be needed anymore
    @GetMapping("home/competitions")
    public ModelAndView showCompetitions(Competition competition, ModelAndView model) {
        User user = getLoggedInUser();
        model.addObject("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.setViewName("home/home");
        return model;
    }

    @GetMapping("home/competitions/edit/{id}")
    public String editCompetitions(@PathVariable("id") int id, Model model) {
        Competition comp = compRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + id));

        model.addAttribute("competition", comp);
        return "home/competitions/edit";
    }

    @GetMapping("home/competitions/view/{id}")
    public String viewCompetition(@PathVariable("id") int id, Model model) {
        Competition comp = compRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + id));

        model.addAttribute("competition", comp);
        return "home/competitions/overview";
    }

    @PostMapping("home/competitions/update/{id}")
    public String updateCompetitions(@PathVariable("id") int id, @Valid Competition comp,
                             BindingResult result, Model model) {

          if (result.hasErrors()) {
            comp.setId(id);
            return "home/competitions/edit";
          }
        User user = getLoggedInUser();
        comp.setAdminID(user.getId());
        compRepository.save(comp);

        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "redirect:/home/home";
    }

    @GetMapping("home/competitions/delete/{id}")
    public String deleteCompetition(@PathVariable("id") int id, Model model) {
        Competition comp = compRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + id));
        compRepository.delete(comp);
        //todo remove categories and participants
        User user = getLoggedInUser();
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "redirect:/home/home";
    }

    // For live updates
    @GetMapping({"home/competitions/update-competitions", "home/update-competitions"})
    public String updateComps(Competition comps, Model model) {
        User user = getLoggedInUser();
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "home/competitions :: #table";
    }

}
