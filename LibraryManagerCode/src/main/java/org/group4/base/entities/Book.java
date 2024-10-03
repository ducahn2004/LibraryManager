package org.group4.base.entities;

import java.util.List;

public class Book {
  private String ISBN;
  private String tittle;
  private String subject;
  private String publisher;
  private String language;
  private int numberOfPages;
  private List<Author> authors;

  public Book(String ISBN, String title, String subject, String publisher, String language,
      int numberOfPages, List<Author> authors) {
    this.ISBN = ISBN;
    this.tittle = title;
    this.subject = subject;
    this.publisher = publisher;
    this.language = language;
    this.numberOfPages = numberOfPages;
    this.authors = authors;
  }

  public String getISBN() {
    return ISBN;
  }

  public String getTitle() {
    return tittle;
  }

  public String getSubject() {
    return subject;
  }

  public String getPublisher() {
    return publisher;
  }

  public String getLanguage() {
    return language;
  }

  public int getNumberOfPages() {
    return numberOfPages;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public void setISBN(String ISBN) {
    this.ISBN = ISBN;
  }

  public void setTitle(String title) {
    this.tittle = title;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setNumberOfPages(int numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

}
