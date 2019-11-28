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
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class CompetitionController {
    @Autowired
    private CompetitionRepository compRepository;
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "home/competitions/new" }, method = RequestMethod.GET)
    public ModelAndView createNewCompetition(ModelAndView model) {
        Competition competition = new Competition();

        model.addObject("competition", competition);
        model.setViewName ("home/addcompetition");
        return model;
    }
    @PostMapping("home/competitions/new")
    public String addCompetition(@Valid Competition competition, BindingResult result, Model model) {
      //todo: check why using ModelAndView is causing errors in the Post Mapping...
        if (result.hasErrors()) {
           // model.setViewName ("home/addcompetition");
            return "home/addcompetition";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        //todo: check dates
        competition.setAdminID(user.getId());
        //todo: check type (if-else) and create # judge login
        compRepository.save(competition);

       /* model.addObject("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.addObject("adminId", user.getId());*/
        //model.setViewName ("home/competitions");
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.addAttribute("adminId", user.getId());
        return "redirect:/home/competitions";
    }

    @GetMapping("home/competitions")
    public ModelAndView showCompetitions(Competition competition, ModelAndView model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addObject("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.setViewName("home/competitions");
        return model;
    }

    @GetMapping("home/competitions/edit/{id}")
    public String editCompetitions(@PathVariable("id") int id, Model model) {
        Competition comp = compRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + id));

        model.addAttribute("competition", comp);
        return "home/updatecompetition";
    }

    @PostMapping("home/competitions/update/{id}")
    public String updateCompetitions(@PathVariable("id") int id, @Valid Competition comp,
                             BindingResult result, Model model) {

          if (result.hasErrors()) {
            comp.setId(id);
            return "home/updatecompetition";
          }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        comp.setAdminID(user.getId());
        compRepository.save(comp);

        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "redirect:/home/competitions";
    }

    @GetMapping("home/competitions/delete/{id}")
    public String deleteCompetition(@PathVariable("id") int id, Model model) {
        Competition comp = compRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + id));
        compRepository.delete(comp);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "redirect:/home/competitions";
    }

    // For live updates
    @GetMapping({"home/competitions/update-competitions", "home/update-competitions"})
    public String updateComps(Competition comps, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "home/competitions :: #table";
    }

}
