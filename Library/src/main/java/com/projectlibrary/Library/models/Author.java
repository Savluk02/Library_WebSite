package com.projectlibrary.Library.models;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    @Column(name = "national")
    private String national;

    @Lob
    @Column(name = "image_author")
    private byte[] imageAuthor;

    @OneToMany(mappedBy = "authorId")
    private List<Book> bookList;

    public Author() {
    }

    public Author(String fullName, String dateOfBirth, String national) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.national = national;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public byte[] getImageAuthor() {
        return imageAuthor;
    }

    public void setImageAuthor(MultipartFile imageAuthor) throws IOException {
        this.imageAuthor = imageAuthor.getBytes();
    }
}

