package org.group4.base.books;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.group4.service.GoogleBooksService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Represents a book with various details like ISBN, title, authors, publisher, etc.
 */
public class Book {

  private final String ISBN;
  private final String tittle;
  private final String subject;
  private final String publisher;
  private final String language;
  private final int numberOfPages;
  private final Set<Author> authors;

  /**
   * Constructor for creating a Book instance with all details.
   *
   * @param ISBN ISBN of the book
   * @param title Title of the book
   * @param subject Subject of the book
   * @param publisher Publisher of the book
   * @param language Language of the book
   * @param numberOfPages Number of pages of the book
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

  // Constructor use Google Books API
  public Book(String ISBN) throws IOException {
    GoogleBooksService googleBooksService = new GoogleBooksService();
    this.ISBN = ISBN;
    try {
      JSONObject bookDetails = googleBooksService.getBookDetails(ISBN);
      this.tittle = bookDetails.getString("title");
      JSONArray categoriesArray = bookDetails.optJSONArray("categories");
      if (categoriesArray != null && !categoriesArray.isEmpty()) {
        this.subject = categoriesArray.getString(0).replace("[", "").replace("]", "").replace("\"", "");
      } else {
        this.subject = "Unknown";
      }
      this.publisher = bookDetails.optString("publisher", "Unknown");
      this.language = bookDetails.optString("language", "Unknown");
      this.numberOfPages = bookDetails.optInt("pageCount", 0);
      this.authors = new HashSet<>();
      JSONArray authorsArray = bookDetails.optJSONArray("authors");
      if (authorsArray != null) {
        for (int i = 0; i < authorsArray.length(); i++) {
          authors.add(new Author(authorsArray.getString(i)));
        }
      }
    } catch (IOException e) {
      throw new IOException("No book found with the given ISBN");
    }
  }
  // Getter methods
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

  public Set<Author> getAuthors() {
    return authors;
  }

  /**
   * Converts authors' names into a comma-separated string.
   *
   * @return A string representation of authors
   */
  public String authorsToString() {
    return String.join(", ", authors.stream().map(Author::getName).toArray(String[]::new));
  }

}
