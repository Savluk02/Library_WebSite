package com.projectlibrary.Library.controllers;

import com.projectlibrary.Library.models.Author;
import com.projectlibrary.Library.models.Book;
import com.projectlibrary.Library.repositories.AuthorRepository;
import com.projectlibrary.Library.repositories.BookRepository;
import com.projectlibrary.Library.services.AuthorService;
import com.projectlibrary.Library.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/author")
public class AuthorController {

    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;
    private final BookService bookService;


    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/add")
    public String mainAuthor(@ModelAttribute Author author) {
        return "author/add";
    }


    @PostMapping("/add")
    public String addAuthor(@ModelAttribute("author") Author author, @RequestParam("imageAuthor") MultipartFile imageAuthor) throws IOException {
        author.setImageAuthor(imageAuthor);
        authorService.addAuthor(author);
        return "redirect:/main/main";
    }


    @GetMapping("/{id}")
    public String showAuthor(@PathVariable("id") int authorId, Model model) {
        logger.debug("Fetching author details for ID: {}", authorId);
        Author author = authorService.findById(authorId);

        model.addAttribute("author", author);

        model.addAttribute("author", authorService.findById(authorId));
        return "author/show";
    }

    @GetMapping("/display-author-image/{id}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> displayAuthorImage(@PathVariable("id") int authorId) {
        try {
            logger.debug("Fetching author image for ID: {}", authorId);
            Author author = authorService.findById(authorId);
            if (author != null && author.getImageAuthor() != null) {
                byte[] imageData = author.getImageAuthor();
                logger.debug("Image size: {} bytes", imageData.length);
                ByteArrayResource resource = new ByteArrayResource(imageData);

                return ResponseEntity.ok()
                        .contentLength(imageData.length)
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }
        } catch (Exception e) {
            logger.error("Error fetching author image for ID: {}", authorId, e);
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/addBook")
    public String mainBook(@ModelAttribute("book") Book book,
                           @PathVariable("id") int authorId, Model model) {
        model.addAttribute("book", book);
        model.addAttribute("author", authorService.findById(authorId));
        return "author/addBook";
    }

    @PostMapping("/{id}/addBook")
    public String addBook(@ModelAttribute("book") Book book,
                          @PathVariable("id") int authorId, Model model) {
        Author author = authorService.findById(authorId);
        bookService.addBook(book, author);
        model.addAttribute("book", book);

        return "redirect:/main/main";
    }


    @PostMapping("/{id}")
    public String delete(@PathVariable("id") int authorId){
        authorService.deleteAuthor(authorId);
        return "redirect:/main/main";
    }
}
