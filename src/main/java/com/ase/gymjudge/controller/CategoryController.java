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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CompetitionRepository compRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ParticipantsRepository participantsRepository;


    @GetMapping("home/competitions/view/category/new/{comp_id}")
    public ModelAndView newCategory(@PathVariable("comp_id") int comp_id, ModelAndView model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));

        model.addObject("comp_id", comp_id);
        model.addObject("category", new Category());
        model.setViewName("home/competitions/category/new");
        return model;
    }

    @PostMapping("home/competitions/view/category/new/{comp_id}")
    public String addCategory(@Valid Category category, @PathVariable("comp_id") int comp_id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comp_id", comp_id);
            return "home/competitions/category/new";
        }
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));

        //add it to category table
        category.setCompetition(comp);
        categoryRepository.save(category);

        //add it to competition table
        List<Category> categories = comp.getCategories();
        categories.add(category);
        comp.setCategories(categories);
        compRepository.save(comp);

        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }


    @GetMapping("home/competitions/view/category/edit/{comp_id}/{cat_id}")
    public ModelAndView editCategory(@PathVariable("comp_id") int comp_id, @PathVariable("cat_id") int cat_id, ModelAndView model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Category cat = categoryRepository.findById(cat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + cat_id));

        model.addObject("comp_id", comp_id);
        model.addObject("category", cat);
        model.setViewName("home/competitions/category/edit");
        return model;
    }

    @PostMapping("home/competitions/view/category/edit/{comp_id}/{cat_id}")
    public String editCategory(@Valid Category category, @PathVariable("comp_id") int comp_id, @PathVariable("cat_id") int cat_id, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comp_id", comp_id);
            //todo not sure if it works
            model.addAttribute("category", category);
            return "home/competitions/category/edit";
        }
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Category cat = categoryRepository.findById(cat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + cat_id));

        //add it to category table
        category.setCompetition(comp);
        category.setId(cat_id);
        categoryRepository.save(category);

        //add it to competition table
        List<Category> categories = comp.getCategories();
        categories.add(category);
        comp.setCategories(categories);
        compRepository.save(comp);

        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }

    @GetMapping("home/competitions/view/category/delete/{comp_id}/{cat_id}")
    public String deleteCompetition(@PathVariable("comp_id") int comp_id, @PathVariable("cat_id") int cat_id, Model model) {
        Competition comp = compRepository.findById(comp_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + comp_id));
        Category cat = categoryRepository.findById(cat_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competition Id:" + cat_id));

        //removes all categories connected and all participants connected to those!
        categoryRepository.delete(cat);

        return "redirect:/home/competitions/view/" + String.valueOf(comp_id);
    }



}
