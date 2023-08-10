package com.projectlibrary.Library.services;

import com.projectlibrary.Library.models.Author;
import com.projectlibrary.Library.models.Book;
import com.projectlibrary.Library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void addBook(Book book, Author author){
//        bookRepository.save(book);
        book.setAuthorId(author);
        if (book.getId() != 0) {
            bookRepository.save(book); // Оновити існуючий запис
        } else {
            bookRepository.save(book); // Додати новий запис
        }
    }

    @Transactional
    public List<Book> mainPageListBook(Author author){
        if(author.getBookList() != null) {
            return bookRepository.findBookByAuthorId(author);
        }
        return Collections.emptyList();
    }

    @Transactional
    public List<Book> allBook(){
        return bookRepository.findAll();
    }

}
