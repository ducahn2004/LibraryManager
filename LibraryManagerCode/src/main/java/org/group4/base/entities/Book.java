package org.group4.base.entities;

import java.util.List;

/**
 * Dai dien cho sach, luu tru cac thong tin chung cua sach.
 *
 */
public class Book {
  private String ISBN; // Ma so quoc te cua sach.
  private String tittle; // Tieu de cua sach.
  private String subject; // Chu de cua sach.
  private String publisher; // Nha xuat ban cua sach.
  private String language; // Ngon ngu cua sach.
  private int numberOfPages; // So trang cua sach.
  private List<Author> authors; // Danh sach cac tac gia cua sach.

  /**
   * Tao mot cuon sach moi.
   * @param ISBN Ma so quoc te cua cuon sach.
   * @param title Tieu de cua cuon sach.
   * @param subject Chu de cua cuon sach.
   * @param publisher Nha xuat ban cua cuon sach.
   * @param language Ngon ngu cua cuon sach.
   * @param numberOfPages So trang cua cuon sach.
   * @param authors Tac gia cua cuon sach.
   */
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

  public List<Author> getAuthors() {
    return authors;
  }

}
