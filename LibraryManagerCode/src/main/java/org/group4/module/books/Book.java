package org.group4.module.books;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a book with details including ISBN, title, subject, publisher,
 * language, number of pages, and associated authors.
 * <p>This class encapsulates all attributes related to a book entity, enabling
 * easy access and modification of details about a book.</p>
 */
public class Book {

  /** ISBN of the book, serving as a unique identifier. */
  private String ISBN;

  /** Title of the book. */
  private String title;

  /** Subject or genre of the book. */
  private String subject;

  /** Publisher of the book. */
  private String publisher;

  /** Language in which the book is written. */
  private String language;

  /** Number of pages contained in the book. */
  private int numberOfPages;

  /** Set of authors who contributed to the book. */
  private Set<Author> authors;

  /**
   * Constructs a {@code Book} instance with all specified details.
   *
   * @param ISBN the ISBN of the book
   * @param title the title of the book
   * @param subject the subject or genre of the book
   * @param publisher the publisher of the book
   * @param language the language of the book
   * @param numberOfPages the number of pages in the book
   * @param authors the set of authors who contributed to the book; if null, an empty set is used
   */
  public Book(String ISBN, String title, String subject, String publisher, String language,
      int numberOfPages, Set<Author> authors) {
    this.ISBN = ISBN;
    this.title = title;
    this.subject = subject;
    this.publisher = publisher;
    this.language = language;
    this.numberOfPages = numberOfPages;
    this.authors = Objects.requireNonNullElse(authors, new HashSet<>());
//    this.authors = authors;
  }


  /**
   * Returns the ISBN of the book.
   *
   * @return the ISBN as a {@code String}
   */
  public String getISBN() {
    return ISBN;
  }

  /**
   * Returns the title of the book.
   *
   * @return the title as a {@code String}
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the subject or genre of the book.
   *
   * @return the subject as a {@code String}
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Returns the publisher of the book.
   *
   * @return the publisher as a {@code String}
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * Returns the language in which the book is written.
   *
   * @return the language as a {@code String}
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Returns the number of pages in the book.
   *
   * @return the number of pages as an {@code int}
   */
  public int getNumberOfPages() {
    return numberOfPages;
  }

  /**
   * Returns the set of authors who contributed to the book.
   *
   * @return a {@code Set} of {@code Author} objects
   */
  public Set<Author> getAuthors() {
    return authors;
  }

  /**
   * Sets the ISBN of the book.
   *
   * @param ISBN the new ISBN to set
   */
  public void setISBN(String ISBN) {
    this.ISBN = ISBN;
  }

  /**
   * Sets the title of the book.
   *
   * @param title the new title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Sets the subject or genre of the book.
   *
   * @param subject the new subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Sets the publisher of the book.
   *
   * @param publisher the new publisher to set
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  /**
   * Sets the language in which the book is written.
   *
   * @param language the new language to set
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Sets the number of pages in the book.
   *
   * @param numberOfPages the new number of pages to set
   */
  public void setNumberOfPages(int numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  /**
   * Sets the authors of the book.
   *
   * @param authors the new set of authors; if null, an empty set is used
   */
  public void setAuthors(Set<Author> authors) {
    this.authors = Objects.requireNonNullElse(authors, new HashSet<>());
//    this.authors = authors;
  }

  /**
   * Returns a comma-separated string of the authors' names for display purposes.
   *
   * @return a {@code String} containing the names of the authors, separated by commas
   */
  public String authorsToString() {
    return authors.stream()
        .map(Author::getName)
        .collect(Collectors.joining(", "));
  }

  @Override
  public String toString() {
    return "Book{" +
        "ISBN='" + ISBN + '\'' +
        ", title='" + title + '\'' +
        ", subject='" + subject + '\'' +
        ", publisher='" + publisher + '\'' +
        ", language='" + language + '\'' +
        ", numberOfPages=" + numberOfPages +
        ", authors=" + authors +
        '}';
  }
}
