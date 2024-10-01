package org.group4.base;

import java.util.List;

public class Author {
  private String name;
  private String description;
  private List<Book> books;

  public Author(String name, String description, List<Book> books) {
    this.name = name;
    this.description = description;
    this.books = books;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

}
