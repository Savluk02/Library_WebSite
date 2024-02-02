package com.projectlibrary.Library.controllers;

import com.projectlibrary.Library.models.Author;
import com.projectlibrary.Library.models.Book;
import com.projectlibrary.Library.services.AuthorService;
import com.projectlibrary.Library.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


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

//    @MessageMapping("/update-search")
//    public void updateSearch(){
//
//    }


    @PostMapping("/search-book")
    @MessageMapping("/books")
    @SendTo("/topic/book")
    public ResponseEntity<List<Book>> searchBook(@RequestParam("nameOfBook") String nameOfBook, Model model) throws JSONException {
        List<Book> books = new ArrayList<>();

        try {
            RestTemplate restTemplate = new RestTemplate();
            String key = "AIzaSyA0a4psXqswxlFKSBeUiS4BgTditty5L9s";
            String URL = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(nameOfBook, StandardCharsets.UTF_8) + "&key=" + key;

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
            JSONObject jsonObject = new JSONObject(responseEntity.getBody());
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                int pageCount = volumeInfo.getInt("pageCount");
                int publishedDate = volumeInfo.getInt("publishedDate");
                books.add(new Book(title, pageCount, publishedDate));
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(books);
    }

//    @PostMapping("/{id}/addBook")
//    public String addBook(@ModelAttribute("book") Book book,
//                          @PathVariable("id") int authorId, Model model) {
//        Author author = authorService.findById(authorId);
//        bookService.addBook(book, author);
//        model.addAttribute("book", book);
//
//        return "redirect:/main/main";
//    }


    @PostMapping("/{id}")
    public String delete(@PathVariable("id") int authorId){
        authorService.deleteAuthor(authorId);
        return "redirect:/main/main";
    }
}
