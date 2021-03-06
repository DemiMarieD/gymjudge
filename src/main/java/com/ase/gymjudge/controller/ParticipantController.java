package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Participants;
import com.ase.gymjudge.repositories.CategoryRepository;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.ParticipantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ParticipantController {
    @Autowired
    private CompetitionRepository compRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ParticipantsRepository participantsRepository;


    @RequestMapping(value = {"/home/competitions/view/gymnasts/new/{comp_id}"}, method = RequestMethod.GET)
    public String addParticipants(@PathVariable("comp_id") int comp_id, Model model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));

        model.addAttribute("competition", comp);
        model.addAttribute("participant", new Participants());
        return "home/competitions/gymnasts/new";
    }

    @PostMapping("/home/competitions/view/gymnasts/new/{comp_id}")
    public String addNewParticipant(@Valid Participants participant, BindingResult result,  @PathVariable("comp_id") int comp_id, Model model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));

        if (result.hasErrors()) {
            model.addAttribute("competition", comp);
            model.addAttribute("participant", participant);
            model.addAttribute("msg", "Error, not everything is filled out correctly!");
            return "home/competitions/gymnasts/new";
        }
        if(participant.getAge() == 0){
            model.addAttribute("competition", comp);
            model.addAttribute("participant", participant);
            model.addAttribute("msg", "Error, birthday needs to be more than one year in the past!");
            return "home/competitions/gymnasts/new";
        }
        //add participant
        participant.setCompetition(comp);
        participantsRepository.save(participant);

        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }

    @GetMapping("/home/competitions/view/gymnasts/edit/{comp_id}/{pat_id}")
    public String editParticipants(@PathVariable("comp_id") int comp_id, @PathVariable("pat_id") int pat_id, Model model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Participants pat = participantsRepository.findById(pat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + pat_id));

        model.addAttribute("competition", comp);
        model.addAttribute("participant", pat);
        return "home/competitions/gymnasts/edit";
    }

    @PostMapping("/home/competitions/view/gymnasts/edit/{comp_id}/{pat_id}")
    public String editParticipant(@Valid Participants participant, @PathVariable("comp_id") int comp_id, @PathVariable("pat_id") int pat_id, BindingResult result, Model model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Participants pat = participantsRepository.findById(pat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + pat_id));

        if (result.hasErrors()) {
            model.addAttribute("competition", comp);
            model.addAttribute("participant", pat);
            model.addAttribute("msg", "Error, not everything is filled out correctly!");
            return "home/competitions/gymnasts/new";
        }
        if(participant.getAge() == 0){
            model.addAttribute("competition", comp);
            model.addAttribute("participant", participant);
            model.addAttribute("msg", "Error, birthday needs to be more than one year in the past!");
            return "home/competitions/gymnasts/new";
        }
        //add participant
        participant.setId(pat_id);
        participant.setCompetition(comp);
        participantsRepository.save(participant);

        model.addAttribute("competition", comp);
        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }


    @GetMapping("home/competitions/view/gymnasts/delete/{comp_id}/{pat_id}")
    public String deleteCompetition(@PathVariable("comp_id") int comp_id, @PathVariable("pat_id") int pat_id, Model model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Participants pat = participantsRepository.findById(pat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + pat_id));

        participantsRepository.delete(pat);
        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }


}
