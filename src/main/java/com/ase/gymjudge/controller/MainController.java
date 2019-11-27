package com.ase.gymjudge.controller;
//import org.springframework.http.ResponseEntity;
import com.ase.gymjudge.entities.Admin;
import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.repositories.AdminRepository;
import com.ase.gymjudge.repositories.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Random;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

/*@Controller
public class MainController {
    @Autowired // This means to get the bean  XYRepository which is auto-generated by Spring
    private AdminRepository adminRepository;
    @Autowired
    private CompetitionRepository compRepository;

    @GetMapping({"/", "/index"})
    public String home(){
        return "index";
    }

    //ADMIN(S)
    @RequestMapping(value = {"/register" }, method = RequestMethod.GET)
    public String showSignUpForm(Model model) {
        Admin newAdmin = new Admin();
        model.addAttribute("newAdmin", newAdmin);
        return "addAdmin";
    }
    @PostMapping("/addadmin")
    public String addUser(@Valid Admin newAdmin, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addAdmin";
        }
        //todo if login is unique
        adminRepository.save(newAdmin);
        return "showCompetitions";
    }


    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String showLogin(Model model) {
        model.addAttribute("admin", new Admin());
        return "login";
    }
    @PostMapping("/login")
    public String login(@Valid Admin admin, BindingResult result, Model model) {

        if(adminRepository.existsAdminByUsername(admin.getLogin()).isEmpty()){
            return "login";
            //todo return error message
        }else{
            if(adminRepository.getPasswordFromUser(admin.getLogin()).equals(admin.getPassword())){
                Integer userId = adminRepository.getIdFromUser(admin.getLogin());
                model.addAttribute("competitions", compRepository.getAllByAdminID(userId));
                return "showCompetitions";
            }else{
                //todo return error message
            }
        }
        return "login";
    }

    //COMPETITIONS
    @RequestMapping(value = { "/addcompetition" }, method = RequestMethod.GET)
    public String createNewCompetition(Model model) {
        Competition competition = new Competition();
        model.addAttribute("competition", competition);
        return "addCompetition";
    }
    @PostMapping("/addcompetition")
    public String addCompetition(@Valid Competition competition, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addCompetition";
        }
        compRepository.save(competition);
        model.addAttribute("competitions", compRepository.findAll());
        return "showCompetitions";
    }
    @GetMapping("/competitions")
    public String showCompetitions(Competition competition, Model model) {
        model.addAttribute("competitions", compRepository.findAll());
        return "showCompetitions";
    }

    @GetMapping("/update-competitions")
    public String updateComps(Competition comps, Model model) {
        model.addAttribute("competitions", compRepository.findAll());
        return "showCompetitions :: #table";
    }
}*/
