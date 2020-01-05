package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.*;
import com.ase.gymjudge.repositories.*;
import com.ase.gymjudge.services.JudgeService;
import com.ase.gymjudge.services.UserService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.xml.bind.SchemaOutputResolver;
import java.text.ParseException;
import java.util.List;

@Controller
public class ScoringController {
    @Autowired
    private CompetitionRepository compRepository;
    @Autowired
    private CategoryRepository catRepository;
    @Autowired
    private ParticipantsRepository patRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JudgeRepository judgeRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    // entering scores
    /*
    public Judge getJudge() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Judge judge = judgeRepository.findByLogin(auth.getName());
        return judge;
    }
    */

    @GetMapping({"/roundsoverview"})
    public String showRoundsOverview(Model model) {
        // TODO: get real id and apparatus from judge login
        int compId = 4;
        Apparatus app = Apparatus.BODEN;

        if (compRepository.findById(compId).isPresent()) {
            Competition comp = compRepository.findById(compId).get();
            List<Grouping> groups = comp.getGroups();
            model.addAttribute("comp", comp);
            model.addAttribute("score", new Score());
            model.addAttribute("apparatus", app);
            // model.addAttribute("currScores", scoreRepository.getScoresByCompetition(comp.getId()));

            return "judge/scoring/roundOverview";
        }

        return "redirect:/";
    }


    @PostMapping({"/roundsoverview"})
    public void addScore(@RequestParam(name = "patId") int id, @Valid Score score, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.err.println(result.getFieldError());
            // return "judge/scoring/roundOverview";
        }

        score.setParticipants(patRepository.findById(id).orElseThrow(RuntimeException::new));

        // TODO: get real apparatus via judge login
        score.setApparatus(Apparatus.BODEN);

        List<Score> scores = scoreRepository.getScoresByCompetition(score.getParticipants().getCompetition().getId());

        for (Score s : scores) {
            if (s.getParticipants().getId() == score.getParticipants().getId() && s.getApparatus() == score.getApparatus()) {
                System.out.println("Found double and delete it.");
                scoreRepository.delete(s);
            }
        }

        scoreRepository.save(score);

        // return "judge/scoring/roundOverview";
    }

    // showing scores
    @GetMapping("/livescores/{id}")
    public String showGroupOverview(@PathVariable("id") String inId, Model model) {
        int id = -1;

        try {
            id = Integer.parseInt(inId);
        } catch (NumberFormatException e) {
            return "redirect:/";
        }

        if (compRepository.findById(id).isPresent()) {
            List<Score> scores = scoreRepository.getScoresByCompetition(id);
            String compName = compRepository.findById(id).get().getName();
            model.addAttribute("scores", scores);
            model.addAttribute("compName", compName);
            return "livescores";
        }

        return "redirect:/";
    }

    @GetMapping({"/livescores/update-scores/{id}"})
    public String updateScores(@PathVariable("id") int id, Model model) {
        List<Score> scores = scoreRepository.getScoresByCompetition(id);
        model.addAttribute("scores", scores);

        return "livescores :: #scoring";
    }

    /*
    @GetMapping({"testing"})
    public String addEntry(Model model) {
        Score temp = new Score();
        temp.setParticipants(patRepository.findById(7).orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:")));
        temp.setApparatus(Apparatus.SCHWEBEBALKEN);
        temp.setStatus(1);
        temp.setD(1);
        temp.setE1(2);
        temp.setE2(3);
        temp.setE3(4);
        temp.setE4(5);
        temp.setN(0);

        scoreRepository.save(temp);

        return "livescores";
    }
    */
}
