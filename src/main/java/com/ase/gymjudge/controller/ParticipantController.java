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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

/*
    @GetMapping("home/competitions/view/category/participant/{comp_id}/{cat_id}")
    public ModelAndView newParticipants(@PathVariable("comp_id") int comp_id, @PathVariable("cat_id") int cat_id, ModelAndView model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Category cat = categoryRepository.findById(cat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + cat_id));

        model.addObject("comp_id", comp_id);
        model.addObject("cat_id", cat_id);
        model.addObject("participant", new Participants());
        model.setViewName("home/competitions/category/newParticipant");
        return model;
    }
    @PostMapping("home/competitions/view/category/participant/{comp_id}/{cat_id}")
    public String addParticipant(@Valid Participants participant, @PathVariable("comp_id") int comp_id, @PathVariable("cat_id") int cat_id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comp_id", comp_id);
            model.addAttribute("cat_id", cat_id);
            return "redirect:home/competitions/category/newParticipant";
        }
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Category cat = categoryRepository.findById(cat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + cat_id));

        //add it to category table
        participant.setCategory(cat);
        participantsRepository.save(participant);

        //add it to competition table
        List<Participants> participants = cat.getParticipants();
        participants.add(participant);
        cat.setParticipants(participants);
        categoryRepository.save(cat);

        //model.addAttribute("competition", comp);

        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }
*/
    //alternatively
    @GetMapping("/home/competitions/view/gymnasts/new/{comp_id}")
    public ModelAndView addParticipants(@PathVariable("comp_id") int comp_id, ModelAndView model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));

        model.addObject("competition", comp);
        model.addObject("participant", new Participants());
        model.setViewName("home/competitions/gymnasts/new");
        return model;
    }

    @PostMapping("/home/competitions/view/gymnasts/new/{comp_id}")
    public String addNewParticipant(@Valid Participants participant, @PathVariable("comp_id") int comp_id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comp_id", comp_id);
            return "redirect:home/competitions/gymnasts/new";
        }
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));

        //add participant
        participant.setCompetition(comp);
        participantsRepository.save(participant);

        model.addAttribute("competition", comp);
        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }

    @GetMapping("/home/competitions/view/gymnasts/edit/{comp_id}/{pat_id}")
    public ModelAndView editParticipants(@PathVariable("comp_id") int comp_id, @PathVariable("pat_id") int pat_id, ModelAndView model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Participants pat = participantsRepository.findById(pat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + pat_id));

        model.addObject("competition", comp);
        model.addObject("participant", pat);
        model.setViewName("home/competitions/gymnasts/edit");
        return model;
    }

    @PostMapping("/home/competitions/view/gymnasts/edit/{comp_id}/{pat_id}")
    public String editParticipant(@Valid Participants participant, @PathVariable("comp_id") int comp_id, @PathVariable("pat_id") int pat_id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comp_id", comp_id);
            model.addAttribute("pat_id", pat_id);
            return "redirect:home/competitions/gymnasts/edit";
        }
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Participants pat = participantsRepository.findById(pat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + pat_id));

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
