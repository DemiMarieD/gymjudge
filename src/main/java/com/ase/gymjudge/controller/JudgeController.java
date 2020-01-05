package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.*;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.JudgeRepository;
import com.ase.gymjudge.repositories.OrderRepository;
import com.ase.gymjudge.services.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class JudgeController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JudgeRepository judgeRepository;
    @Autowired
    private CompetitionRepository competitionRepository;

    //todo: check whats the problem
    @Autowired
    private JudgeService judgeService;

     /*  @GetMapping("judge/scoring")
    public ModelAndView newCategory(ModelAndView model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Judge judge = judgeService.findByLogin(auth.getName());
        Grouping group = orderRepository.getGroup(round, judge.getApparatus());
        //todo calculate/find the number of rounds
        model.addObject("judge", judge);
        model.setViewName("judge/scoring/roundOverview");

        return model;
    }
    */

/*
   @GetMapping("judge/scoring/{round}")
    public ModelAndView newCategory(@PathVariable("round") int round, ModelAndView model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Judge judge = judgeService.findByLogin(auth.getName());
        Grouping group = orderRepository.getGroup(judge.getCompetition(), round, judge.getApparatus());

        model.addObject("judge", judge);
        model.addObject("group", group);
        model.addObject("scoring", new Scoring());
        model.setViewName("judge/scoring/table");

        return model;
    }
    */

    @GetMapping("home/competitions/view/judges/delete/{comp_id}/{judge_id}")
    public String deleteJudge(@PathVariable("comp_id") int comp_id, @PathVariable("judge_id") int judge_id, Model model) {
        Competition comp = competitionRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Judge judge = judgeRepository.findById(judge_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid judge Id: " + judge_id));
       judgeRepository.delete(judge);
        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }


    @GetMapping("/home/competitions/view/judge/new/{comp_id}")
    public ModelAndView addJudges(@PathVariable("comp_id") int comp_id, ModelAndView model) {
        Competition comp = competitionRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Judge judge = new Judge();
        model.addObject("competition", comp);
        model.addObject("judge", judge);
        model.setViewName("home/competitions/judge/new");
        return model;
    }

    @PostMapping("/home/competitions/view/judge/new/{comp_id}")
    public String addNewJudges(@Valid Judge judge, @PathVariable("comp_id") int comp_id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comp_id", comp_id);
            return "redirect:home/competitions/judge/new";
        }
        Competition comp = competitionRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        judge.setCompetition(comp);
        judgeRepository.save(judge);

        List<Judge> judges = comp.getJudges();
        judges.add(judge);
        comp.setJudges(judges);
        competitionRepository.save(comp);
        model.addAttribute("competition", comp);
        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }



}
