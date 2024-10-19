package org.group4.base.entities;

import java.util.List;

public class Author {
  private final String name;
  private final String description;
  private final List<Book> books;

  // Constructor
  public Author(String name, String description, List<Book> books) {
    this.name = name;
    this.description = description;
    this.books = books;
  }

  // Getter
  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<Book> getBooks() {
    return books;
  }

}
