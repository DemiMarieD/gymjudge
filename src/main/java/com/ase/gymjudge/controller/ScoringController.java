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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.xml.bind.SchemaOutputResolver;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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

            List<Score> currScores = scoreRepository.getScoresByCompetitionId(comp.getId());

            // ordering of grouping TODO: discuss how to handle following rounds
            List<Grouping> orderedGroups = new ArrayList<>();
            for (int i = 0; i < comp.getGroups().get(0).getApparatuses().size(); i++) {
                for (Grouping g : comp.getGroups()) {
                    if (g.getApparatuses().get(i) == app) {
                        orderedGroups.add(g);
                        break;
                    }
                }
            }

            HashMap<Integer, Score> currentScores = new HashMap<>();
            for (Grouping g : orderedGroups) {
                for (Participants p : g.getParticipants()) {
                    boolean hadScore = false;
                    for (Score s : currScores) {
                        if (p == s.getParticipants()) {
                            currentScores.put(p.getId(), s);
                            hadScore = true;
                            break;
                        }
                    }

                    if (!hadScore) {
                        currentScores.put(p.getId(), null);
                    }
                }
            }

            model.addAttribute("compName", comp.getName());
            model.addAttribute("score", new Score());
            model.addAttribute("rounds", orderedGroups);
            model.addAttribute("apparatus", app);
            model.addAttribute("currentScores", currentScores);

            return "judge/scoring/roundOverview";
        }

        return "redirect:/";
    }

    @PostMapping({"/roundsoverview/{status}"})
    public String addScore(@PathVariable("status") int status, @RequestParam(name = "patId") int id, @Valid Score score, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.err.println(result.getFieldError());
            return "judge/scoring/roundOverview";
        }

        score.setParticipants(patRepository.findById(id).orElseThrow(RuntimeException::new));

        // TODO: get real apparatus via judge login
        score.setApparatus(Apparatus.BODEN);
        score.setStatus(status);

        removeScoreIfExists(score);

        scoreRepository.save(score);

        return "redirect:/roundsoverview";
    }

    private void removeScoreIfExists(Score score) {
        List<Score> scores = scoreRepository.getScoresByCompetitionId(score.getParticipants().getCompetition().getId());

        for (Score s : scores) {
            if (s.getParticipants() == score.getParticipants() && s.getApparatus() == score.getApparatus()) {
                scoreRepository.delete(s);
            }
        }
    }

    /*
    @PostMapping({"/roundsoverview/delete/{scoreId}"})
    public String removeScore(@PathVariable("scoreId") int scoreId, Model model) {
        if (scoreRepository.findById(scoreId).isPresent()) {
            scoreRepository.delete(scoreRepository.findById(scoreId).get());
        }

        return "redirect:/roundsoverview";
    }
    */

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
            List<Score> scores = scoreRepository.getActiveScoresByCompetitionId(id);
            String compName = compRepository.findById(id).get().getName();
            model.addAttribute("scores", scores);
            model.addAttribute("compName", compName);
            return "livescores";
        }

        return "redirect:/";
    }

    @GetMapping({"/livescores/update-scores/{id}"})
    public String updateScores(@PathVariable("id") int id, Model model) {
        List<Score> scores = scoreRepository.getActiveScoresByCompetitionId(id);
        model.addAttribute("scores", scores);

        return "livescores :: #scoring";
    }
}
