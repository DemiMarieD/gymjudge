package com.ase.gymjudge.controller;

import com.ase.gymjudge.entities.Category;
import com.ase.gymjudge.entities.Competition;
import com.ase.gymjudge.entities.Role;
import com.ase.gymjudge.entities.User;
import com.ase.gymjudge.repositories.CategoryRepository;
import com.ase.gymjudge.repositories.CompetitionRepository;
import com.ase.gymjudge.repositories.ParticipantsRepository;
import com.ase.gymjudge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CompetitionRepository compRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ParticipantsRepository participantsRepository;


    @GetMapping("home/competitions/view/newcategory/{id}")
    public ModelAndView deleteCompetition(@PathVariable("id") int comp_id, ModelAndView model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        model.addObject("competition", comp);
        model.setViewName("home/competitions/category/new");
        return model;
    }

}
