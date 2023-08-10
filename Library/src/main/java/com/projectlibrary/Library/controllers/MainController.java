package com.projectlibrary.Library.controllers;

import com.projectlibrary.Library.models.Author;
import com.projectlibrary.Library.models.Book;
import com.projectlibrary.Library.models.Person;
import com.projectlibrary.Library.services.AuthorService;
import com.projectlibrary.Library.services.BookService;
import com.projectlibrary.Library.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {

    private final PersonService personService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public MainController(PersonService personService, AuthorService authorService, BookService bookService) {
        this.personService = personService;
        this.authorService = authorService;
        this.bookService = bookService;
    }


    @GetMapping("/main")
    public String mainPage(@ModelAttribute("author") Author author,
                           @ModelAttribute("book") Book book,
                           @ModelAttribute("person") Person person, Model model){
        model.addAttribute("author", authorService.allAuthor());
//        model.addAttribute("book", bookService.mainPageListBook(author));
        model.addAttribute("person", new Person());
        return "main/main";
    }

}
