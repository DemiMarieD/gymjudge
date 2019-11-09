package com.ase.gymjudge.controller;

//import org.springframework.http.ResponseEntity;
import com.ase.gymjudge.form.PersonFormDEMO;
import com.ase.gymjudge.model.PersonDEMO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainControllerDEMO {
    private static List<PersonDEMO> persons = new ArrayList<PersonDEMO>();
    static {
        persons.add(new PersonDEMO("Bill", "Gates"));
        persons.add(new PersonDEMO("Steve", "Jobs"));
    }

    // Aus Application.properties abspritzen (inject).
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "indexDEMO";
    }
    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {

        model.addAttribute("persons", persons);

        return "personListDEMO";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        PersonFormDEMO personForm = new PersonFormDEMO();
        model.addAttribute("personForm", personForm);

        return "addPersonDEMO";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model, //
                             @ModelAttribute("personForm") PersonFormDEMO personForm) {

        String firstName = personForm.getFirstName();
        String lastName = personForm.getLastName();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            PersonDEMO newPerson = new PersonDEMO(firstName, lastName);
            persons.add(newPerson);

            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPersonDEMO";
    }
}
