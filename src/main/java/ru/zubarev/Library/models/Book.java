package ru.zubarev.Library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
private int id;
@NotEmpty(message = "Название книги не должно быть пустым")
@Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов длинной")
private String name;
    @NotEmpty(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя автора быть от 2 до 100 символов длинной")
private String author;
    @Min(value = 1500,message = "Год должен быть больше, чем 1500")
private int yearOfPublication;

    public Book(){}

    public Book(int id, String name, String author, int yearOfPublication) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
}
