package org.group4.base.entities;

import java.util.List;

/**
 * Dai dien cho tac gia cua sach.
 * Mot tac gia co the viet nhieu cuon sach.
 * Moi cuon sach co the co nhieu tac gia.
 */
public class Author {
  private final String name; // Ten tac gia.
  private final String description; // Mo ta ve tac gia.
  private final List<Book> books; // Danh sach cac cuon sach cua tac gia.

  /**
   * Tao tac gia moi.
   * @param name Ten tac gia.
   * @param description Mo ta ve tac gia.
   * @param books Danh sach cac cuon sach cua tac gia.
   */
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

}
