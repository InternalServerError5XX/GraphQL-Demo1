package org.example.Models;

public class Book
{
    private int id;
    private String title;
    private Author author;

    public Book() {}
    public Book(int id, String title, Author author)
    {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public Author getAuthor() { return author; }

    public void setTitle(String value) { title = value; }
}
