package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.*;
import com.ase.gymjudge.repositories.*;
import com.ase.gymjudge.services.JudgeService;
import com.ase.gymjudge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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

    /*
    public Judge getJudge() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Judge judge = judgeRepository.findByLogin(auth.getName());
        return judge;
    }
    */

    @GetMapping("livescores/{id}")
    public String showGroupOverview(@PathVariable("id") int id, Model model) {
        List<Score> scores = scoreRepository.getScoresByCompetition(id);
        model.addAttribute("scores", scores);
        return "livescores";
    }

    @GetMapping({"update-scores/{id}"})
    public String updateScores(@PathVariable("id") int id, Model model) {
        List<Score> scores = scoreRepository.getScoresByCompetition(id);
        model.addAttribute("scores", scores);
        return "livescores :: #scoreTable";
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
