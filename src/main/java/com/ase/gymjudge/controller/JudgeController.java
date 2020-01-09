package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.Apparatus;
import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Judge;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.JudgeRepository;
import com.ase.gymjudge.repositories.OrderRepository;
import com.ase.gymjudge.services.JudgeService;
import com.ase.gymjudge.services.JudgeServiceImpl;
import com.ase.gymjudge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class JudgeController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private JudgeRepository judgeRepository;
    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private UserService userService;

    private List<User> judges;
    //todo: check whats the problem
    @Autowired
    private JudgeService judgeService;

    private String role = "JUDGE";


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
    public User getLoggedInJudges() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User judge = judgeService.findByEmail(auth.getName());

        return judge;
    }

   /* @GetMapping("home/competitions/view/{comp_id}/{judge_id}")
    public String deleteJudge(@PathVariable("comp_id") int comp_id, @PathVariable("judge_id") int judge_id, Model model) {
        Competition comp = competitionRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        User judge = judgeRepository.findById(judge_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid judge Id: " + judge_id));
        judgeRepository.delete(judge);
        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }
    */
    @GetMapping("/home/competitions/view/{comp_id}")
    public ModelAndView addJudges(@PathVariable("comp_id") int comp_id, ModelAndView model) {
        Competition comp = competitionRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        User judge = new User();
        model.addObject("competition", comp);
        model.addObject("judge", judge);
        model.setViewName("home/competitions");
        return model;
    }

    @PostMapping("/home/competitions/view/{comp_id}")
    public String addNewJudges(@Valid User judge, @PathVariable("comp_id") int comp_id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comp_id", comp_id);
            return "redirect:home/competitions";
        }
        Competition comp = competitionRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));

        judgeService.create(comp);
        judgeRepository.save(judge);

        comp.setJudges(judges);
        competitionRepository.save(comp);
        model.addAttribute("competition", comp);
        model.addAttribute("judge", judgeRepository.findById(judge.getId()));
        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }

}
