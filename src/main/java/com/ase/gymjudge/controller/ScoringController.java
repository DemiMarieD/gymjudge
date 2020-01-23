package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.*;
import com.ase.gymjudge.repositories.*;
import com.ase.gymjudge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private GroupRepository groupRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    // entering scores

    public User getJudge() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User judge = userService.findByEmail(auth.getName());
        return judge;
    }
    @GetMapping({"/roundsoverview"})
    public String showRoundsOverview(Model model) {
        int compId = getJudge().getCompetition().getId();
        Apparatus app = getJudge().getApparatus();
        //this could be made shorter?!
        if (compRepository.findById(compId).isPresent()) {
            Competition comp = compRepository.findById(compId).get();

            List<Score> currScores = scoreRepository.getScoresByCompetitionId(comp.getId());

            // ordering of grouping TODO: discuss how to handle following rounds
            List<Grouping> orderedGroups = comp.getGroupingOrderFor(app);

            HashMap<Integer, Score> currentScores = new HashMap<>();
            for (Grouping g : orderedGroups) {
                if (g != null) {
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

        int existingScoreId = findScoreIdIfExists(score);

        if (scoreRepository.findById(existingScoreId).isPresent()) {
            Score existingScore = scoreRepository.findById(existingScoreId).get();
            existingScore.setD(score.getD());
            existingScore.setE1(score.getE1());
            existingScore.setE2(score.getE2());
            existingScore.setE3(score.getE3());
            existingScore.setE4(score.getE4());
            existingScore.setN(score.getN());
            existingScore.setStatus(status);
            scoreRepository.save(existingScore);
        } else {
            scoreRepository.save(score);
        }

        return "redirect:/roundsoverview";
    }

    @PostMapping({"/roundsoverview/delete/{scoreId}"})
    public String deleteScore(@PathVariable("scoreId") int scoreId, Model model) {
        if (scoreRepository.findById(scoreId).isPresent()) {
            scoreRepository.delete(scoreRepository.findById(scoreId).get());
        }

        return "redirect:/roundsoverview";
    }

    private int findScoreIdIfExists(Score score) {
        List<Score> scores = scoreRepository.getScoresByCompetitionId(score.getParticipants().getCompetition().getId());

        for (Score s : scores) {
            if (s.getParticipants() == score.getParticipants() && s.getApparatus() == score.getApparatus()) {
                return s.getId();
            }
        }

        return -1;
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
            Competition comp = compRepository.findById(id).get();

            ArrayList<Score> scores = new ArrayList<>();

            for (Participants p : comp.getParticipants()) {
                scores.addAll(scoreRepository.getActiveScoresByParticipantId(p.getId()));
            }

            String compName = comp.getName();
            model.addAttribute("scores", scores);
            model.addAttribute("compName", compName);
            return "livescores";
        }

        return "redirect:/";
    }

    @GetMapping({"/livescores/update-scores/{id}"})
    public String updateScores(@PathVariable("id") int id, Model model) {
        ArrayList<Score> scores = new ArrayList<>();
        if (compRepository.findById(id).isPresent()) {
            Competition comp = compRepository.findById(id).get();

            for (Participants p : comp.getParticipants()) {
                scores.addAll(scoreRepository.getActiveScoresByParticipantId(p.getId()));
            }
        }

        model.addAttribute("scores", scores);

        return "livescores :: #scoring";
    }
}
