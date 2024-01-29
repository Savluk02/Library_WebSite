package com.projectlibrary.Library.services;

import com.projectlibrary.Library.models.Author;
import com.projectlibrary.Library.repositories.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

    private final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public List<Author> allAuthor(){
        return authorRepository.findAll();
    }


    @Transactional
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    @Transactional
    public Author findById(int id){
        logger.debug("Fetching author by ID: {}", id);
        return authorRepository.findById(id);
    }


    @Transactional
    public void deleteAuthor(int id){
        authorRepository.deleteAuthorById(id);
    }
}
