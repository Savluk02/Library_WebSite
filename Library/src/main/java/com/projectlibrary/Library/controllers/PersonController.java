package com.projectlibrary.Library.controllers;

import com.projectlibrary.Library.models.Person;
import com.projectlibrary.Library.repositories.PeopleRepository;
import com.projectlibrary.Library.security.PersonDetails;
import com.projectlibrary.Library.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.PastOrPresent;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PeopleRepository peopleRepository;
    private final PersonService personService;

    @Autowired
    public PersonController(PeopleRepository peopleRepository, PersonService personService) {
        this.peopleRepository = peopleRepository;
        this.personService = personService;
    }

    @GetMapping("/person")
    public String mainPage(@AuthenticationPrincipal PersonDetails personDetails, Model model){
//        Person person = new Person();
        model.addAttribute("user", personDetails);
        return"person/person";
    }
}
