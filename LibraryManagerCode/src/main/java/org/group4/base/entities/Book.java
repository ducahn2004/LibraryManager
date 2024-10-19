package org.group4.base.entities;

import java.util.List;

public class Book {
  private final String ISBN;
  private final String tittle;
  private final String subject;
  private final String publisher;
  private final String language;
  private final int numberOfPages;
  private final List<Author> authors;

  // Constructor
  public Book(String ISBN, String title, String subject, String publisher, String language, int numberOfPages,
      List<Author> authors) {
    this.ISBN = ISBN;
    this.tittle = title;
    this.subject = subject;
    this.publisher = publisher;
    this.language = language;
    this.numberOfPages = numberOfPages;
    this.authors = authors;
  }

  // Getter
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

  public void printDetails() {
    System.out.println("ISBN: " + getISBN());
    System.out.println("Title: " + getTitle());
    System.out.println("Subject: " + getSubject());
    System.out.println("Publisher: " + getPublisher());
    System.out.println("Language: " + getLanguage());
    System.out.println("Number of pages: " + getNumberOfPages());
    System.out.println("Authors: ");
    for (Author author : authors) {
      System.out.println(author.getName());
    }
  }


}
