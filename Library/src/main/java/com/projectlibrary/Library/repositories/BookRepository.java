package com.projectlibrary.Library.repositories;

import com.projectlibrary.Library.models.Author;
import com.projectlibrary.Library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findBookByAuthorId(Author authorId);
}
