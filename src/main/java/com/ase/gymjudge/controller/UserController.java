package com.ase.gymjudge.controller;
import javax.validation.Valid;

import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.RoleRepository;
import com.ase.gymjudge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class UserController {

    @Autowired
    private CompetitionRepository compRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    //todo take care of logout

    @RequestMapping(value= {"/login"}, method=RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.setViewName("user/login");
        return model;
    }

    @RequestMapping(value= {"/signup"}, method=RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        model.setViewName("user/signup");

        return model;
    }

    @RequestMapping(value= {"/signup"}, method=RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = userService.findByEmail(user.getEmail());

        if(userExists != null) {
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if(bindingResult.hasErrors()) {
            model.setViewName("user/signup");
        } else {
            userService.save(user);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("user", new User());
            model.setViewName("user/login");
        }

        return model;
    }

    public User getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        return user;
    }

    @RequestMapping(value= {"/home"}, method=RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("competitions", compRepository.getCompetitionsByUserId(user.getId()));
        model.addAttribute("userName", user.getFirstname() + " " + user.getLastname());
        return "home/home";
    }

    @RequestMapping(value= {"/login_successful"}, method=RequestMethod.GET)
    public String login_successful(){
        if(getLoggedInUser().getRoles().getId() == roleRepository.findByRole("ADMIN").getId()) {  //.contains(roleRepository.findByRole("ADMIN")
            return "redirect:/home";
        }else{
            return "redirect:/roundsoverview";
        }
    }

    @RequestMapping(value= {"/access_denied"}, method=RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("error/access_denied");
        return model;
    }

    @RequestMapping(value= {"/logout"}, method=RequestMethod.GET)
    public String logout() {
         return "redirect:/index";
    }

}