package org.group4.base.books;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a book with various details like ISBN, title, authors, publisher, etc.
 * This class encapsulates information about a book, including ISBN, title, subject,
 * publisher, language, number of pages, and authors.
 */
public class Book {

  // ISBN of the book, unique identifier
  private String ISBN;

  // Title of the book
  private String tittle;

  // Subject of the book
  private String subject;

  // Publisher of the book
  private String publisher;

  // Language of the book
  private String language;

  // Number of pages in the book
  private int numberOfPages;

  // Set of authors who have written the book
  private Set<Author> authors;

  /**
   * Constructor for creating a Book instance with all details.
   *
   * @param ISBN ISBN of the book
   * @param title Title of the book
   * @param subject Subject of the book
   * @param publisher Publisher of the book
   * @param language Language of the book
   * @param numberOfPages Number of pages in the book
   * @param authors Set of authors of the book
   */
  public Book(String ISBN, String title, String subject, String publisher, String language, int numberOfPages,
      Set<Author> authors) {
    this.ISBN = ISBN;
    this.tittle = title;
    this.subject = subject;
    this.publisher = publisher;
    this.language = language;
    this.numberOfPages = numberOfPages;
    this.authors = new HashSet<>(authors); // Ensure immutability of the authors set
  }

  /**
   * Returns the ISBN of the book.
   *
   * @return ISBN as a String
   */
  public String getISBN() {
    return ISBN;
  }

  /**
   * Returns the title of the book.
   *
   * @return Title as a String
   */
  public String getTitle() {
    return tittle;
  }

  /**
   * Returns the subject of the book.
   *
   * @return Subject as a String
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Returns the publisher of the book.
   *
   * @return Publisher as a String
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * Returns the language in which the book is written.
   *
   * @return Language as a String
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Returns the number of pages in the book.
   *
   * @return Number of pages as an integer
   */
  public int getNumberOfPages() {
    return numberOfPages;
  }

  /**
   * Returns the set of authors who wrote the book.
   *
   * @return Set of authors (Author objects)
   */
  public Set<Author> getAuthors() {
    return authors;
  }
  
  /**
   * Sets the ISBN of the book.
   *
   * @param ISBN The new ISBN to set
   */
  public void setISBN(String ISBN) {
    this.ISBN = ISBN;
  }

  /**
   * Sets the title of the book.
   *
   * @param title The new title to set
   */
  public void setTitle(String title) {
    this.tittle = title;
  }

  /**
   * Sets the subject of the book.
   *
   * @param subject The new subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Sets the publisher of the book.
   *
   * @param publisher The new publisher to set
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  /**
   * Sets the language of the book.
   *
   * @param language The new language to set
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * Sets the number of pages of the book.
   *
   * @param numberOfPages The new number of pages to set
   */
  public void setNumberOfPages(int numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  /**
   * Sets the authors of the book.
   *
   * @param authors The new set of authors to set
   */
  public void setAuthors(Set<Author> authors) {
    this.authors = authors;
  }

  /**
   * Converts authors' names into a comma-separated string.
   * This method is useful for displaying a simple string representation of the authors.
   *
   * @return A string representation of authors' names, separated by commas
   */
  public String authorsToString() {
    return String.join(", ", authors.stream().map(Author::getName).toArray(String[]::new));
  }

}
