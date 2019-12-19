package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.*;
import com.ase.gymjudge.repositories.OrderRepository;
import com.ase.gymjudge.services.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JudgeController {
    @Autowired
    private OrderRepository orderRepository;

    //todo: check whats the problem
    @Autowired
 //   private JudgeService judgeService;

    @RequestMapping(value= {"/judge/login"}, method= RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.setViewName("judge/login");
        return model;
    }

  /*  @GetMapping("judge/scoring/{round}")
    public ModelAndView newCategory(@PathVariable("round") int round, ModelAndView model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Judge judge = judgeService.findByLogin(auth.getName());
        Grouping group = orderRepository.getGroup(round, judge.getApparatus());

        model.addObject("judge", judge);
        model.addObject("group", group);
        model.addObject("scoring", new Scoring());
        model.setViewName("judge/scoring/table");

        return model;
    }
    */

}
