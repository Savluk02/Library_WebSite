package com.projectlibrary.Library.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.client.RestTemplate;
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

//Створити html сторінку для пошуку
//   Написати запит до Google Books
//    @RequestParam("imageAuthor") MultipartFile imageAuthor,
    @PostMapping("/add")
    public String addAuthor(@ModelAttribute("author") Author author,@RequestParam("fullName") String fullName,
                             Model model) throws IOException {
//        author.setImageAuthor(imageAuthor);
//        authorService.addAuthor(author);
        System.out.println("Ім'я автора: " + fullName);

        String apiKey = "AIzaSyA0a4psXqswxlFKSBeUiS4BgTditty5L9s";
        String url = "https://www.googleapis.com/books/v1/volumes?q=inauthor:" + fullName + "&key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()){
            String responseBody = response.getBody();
            // Обробка отриманих даних з API
            // Наприклад, ви можете використовувати Jackson або Gson для розбору JSON в об'єкти Java
            // Приклад розбору JSON в об'єкти за допомогою Jackson:
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode root = objectMapper.readTree(responseBody);
                // Тут можна обробити отримані дані та передати їх у модель для відображення на сторінці
                model.addAttribute("searchResults", root);
                // Приклад передачі даних у модель
                System.out.println("Дані отримано");

            } catch (JsonProcessingException e) {
                // Обробка помилок розбору JSON
                System.out.println("Помилка з даними");
                e.printStackTrace();
            }
        } else {
            // Обробка помилки виклику Google Books API
            System.out.println("Помилка виклику Google Books API: " + response.getStatusCode());
        }

        return "author/search";
    }

    @GetMapping("/search")
    public String searchAuthor(){
        return "author/search";
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
