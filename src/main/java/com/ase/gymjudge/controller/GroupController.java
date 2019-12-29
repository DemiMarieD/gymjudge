package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.Grouping;
import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.GroupRepository;
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
public class GroupController {
    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ParticipantsRepository participantsRepository;

    @GetMapping("home/competitions/view/group/new/{comp_id}")
    public ModelAndView deleteGroup(@PathVariable("comp_id") int comp_id, ModelAndView model) {
        Competition competition = competitionRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id: " + comp_id));

        model.addObject("comp_id", comp_id);
        model.addObject("group", new Grouping());
        model.setViewName("home/competitions/group/new");
        return model;
    }

    @PostMapping("home/competitions/view/group/new/{comp_id}")
    public String addCompetition(@Valid Grouping group, @PathVariable("comp_id") int comp_id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comp_id", comp_id);
            return "home/competitions/category/new";
        }
        Competition competition = competitionRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id: " + comp_id));

        // add it to group table
        group.setCompetition(competition);
        groupRepository.save(group);

        // add it to competition table
        List<Grouping> groups = competition.getGroups();
        groups.add(group);
        competition.setGroups(groups);
        competitionRepository.save(competition);

        model.addAttribute("competition", competition);
        return "redirect:/home/competitions/view/" + comp_id;
    }
    @GetMapping("/home/groups/view/{group_id}")
    public String viewGroup(@PathVariable("group_id") int group_id, Model model) {
        Grouping grouping = groupRepository.findById(group_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Group Id: " + group_id));

        model.addAttribute("grouping", grouping);
        return "home/competitions/group/overview";
    }
}
