package com.ase.gymjudge.controller;
//import org.springframework.http.ResponseEntity;
import com.ase.gymjudge.entities.*;
import com.ase.gymjudge.repositories.CategoryRepository;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.ParticipantsRepository;
import com.ase.gymjudge.repositories.UserRepository;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class CompetitionController {
    @Autowired
    private CompetitionRepository compRepository;
    @Autowired
    private CategoryRepository catRepository;
    @Autowired
    private ParticipantsRepository patRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


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
            return "home/competitions/new";
        }
        if(competition.getStartDate().compareTo(competition.getEndDate()) > 0){
            competition.emptyDates();
            model.addAttribute("competition", competition);
            model.addAttribute("msg", "Start date is after end date!");
            return "home/competitions/new";
        }
        User user = getLoggedInUser();

        competition.setAdminID(user.getId());
        compRepository.save(competition);

        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.addAttribute("adminId", user.getId());
        return "redirect:/home/competitions/view/" + competition.getId();
    }

    //should not be needed anymore
   /* @GetMapping("home/competitions")
    public ModelAndView showCompetitions(Competition competition, ModelAndView model) {
        User user = getLoggedInUser();
        model.addObject("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.setViewName("home/home");
        return model;
    }*/

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

    @PostMapping("home/competitions/view/{id}")
    public String showCompetition(@PathVariable("id") int id, @Valid Competition comp,
                                  BindingResult result, Model model) {

        Competition competition = compRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + id));

        System.out.println(competition.getId());
        System.out.println(competition.getJudgePassword());
        System.out.println(competition.getType());
        System.out.println(competition.getAvailableApparatuses().toString());

        //creating Judge
        List<Apparatus> apparatuses = competition.getAvailableApparatuses();
        List<User> judges = new LinkedList<>();
        for (Apparatus a : apparatuses){
            if (a != Apparatus.PAUSE) {
                User judge = new User();
                judge.setApparatus(a);
                judge.setCompetition(competition);
                judge.setEmail((a.getDisplayValue() + comp.getId() + "@gymjudge.at").toLowerCase());
                judge.setFirstname(a.getDisplayValue());
                judge.setLastname(competition.getName());
                System.out.println("Generated new Judge:");
                System.out.println(judge.getEmail());

                String password = comp.getJudgePassword(); //todo: set nicer passwords
                judge.setPassword(password);
                judge.setJudgePassword(""); //not hashed
                judge.setActive(1);

                userService.saveJudge(judge);
                judges.add(judge);
            }
        }
        competition.setJudges(judges);
        competition.setJudgePassword("");

        compRepository.save(competition);

        return "redirect:/home/competitions/view/" + String.valueOf(id);
    }

    @PostMapping("home/competitions/update/{id}")
    public String updateCompetitions(@PathVariable("id") int id, @Valid Competition comp,
                             BindingResult result, Model model) {

          if (result.hasErrors()) {
            comp.setId(id);
            return "home/competitions/edit";
          }
        if(comp.getStartDate().compareTo(comp.getEndDate()) > 0){
            comp.emptyDates();
            model.addAttribute("competition", comp);
            model.addAttribute("msg", "Start date is after end date!");
            return "home/competitions/edit";
        }
        User user = getLoggedInUser();
        comp.setAdminID(user.getId());

        if (comp.getStatus() == Status.ACTIVE) {
            if (compRepository.getCompetitionsById(id).getJudges().size() > 0) {
                for (User judge: compRepository.getCompetitionsById(id).getJudges()) {
                    judge.setActive(1);
                }
            }
        } else if (comp.getStatus() == Status.FINISHED) {
            // userRepository.deleteAll(compRepository.getCompetitionsById(id).getJudges());
            for (User judge: compRepository.getCompetitionsById(id).getJudges()) {
                judge.setActive(0);
            }
        }

        compRepository.save(comp);
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "redirect:/home/competitions/view/" + String.valueOf(id);
    }

    @GetMapping("home/competitions/delete/{id}")
    public String deleteCompetition(@PathVariable("id") int id, Model model) {
        Competition comp = compRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + id));

        //delete the judges first
        for (User judge : comp.getJudges()){
            userService.deleteJudge(judge);
        }

        //automatically deletes all categories connected and all participants connected to the competition!
        compRepository.delete(comp);

        User user = getLoggedInUser();
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "redirect:/home";
    }

    // For live updates
    @GetMapping({"home/competitions/update-competitions", "home/update-competitions"})
    public String updateComps(Competition comps, Model model) {
        User user = getLoggedInUser();
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        return "home/competitions :: #table";
    }

}
