package com.projectlibrary.Library.repositories;

import com.projectlibrary.Library.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    List<Author> findAuthorById(int id);
    Author findById(int id);
    void deleteAuthorById(int id);

}