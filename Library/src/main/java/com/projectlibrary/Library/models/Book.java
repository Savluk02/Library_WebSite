package com.projectlibrary.Library.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "nameOfBook")
    private String nameOfBook;

    @Column(name = "dateOfBook")
    private String dateOfBook;

    @Column(name = "num_of_pages")
    private int numOfPages;


    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author authorId;



    public Book() {
    }
    public Book(String nameOfBook, int numOfPages, int datoOfBook){
        this.nameOfBook = nameOfBook;
        this.numOfPages = numOfPages;
        this.dateOfBook = datoOfBook;
    }

    public Book(String nameOfBook, String dateOfBook) {
        this.nameOfBook = nameOfBook;
        this.dateOfBook = dateOfBook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfBook() {
        return nameOfBook;
    }

    public void setNameOfBook(String nameOfBook) {
        this.nameOfBook = nameOfBook;
    }

    public String getDateOfBook() {
        return dateOfBook;
    }

    public void setDateOfBook(String dateOfBook) {
        this.dateOfBook = dateOfBook;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author author) {
        this.authorId = author;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }
}
