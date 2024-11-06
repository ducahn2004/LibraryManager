package org.group4.base.books;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.group4.service.GoogleBooksService;
import org.json.JSONArray;
import org.json.JSONObject;

public class Book {

  private final String ISBN;
  private final String tittle;
  private final String subject;
  private final String publisher;
  private final String language;
  private final int numberOfPages;
  private final Set<Author> authors;

  // Constructor
  public Book(String ISBN, String title, String subject, String publisher, String language, int numberOfPages,
      Set<Author> authors) {
    this.ISBN = ISBN;
    this.tittle = title;
    this.subject = subject;
    this.publisher = publisher;
    this.language = language;
    this.numberOfPages = numberOfPages;
    this.authors = authors;
  }

  // Constructor use Google Books API
  public Book(String ISBN) throws IOException {
    GoogleBooksService googleBooksService = new GoogleBooksService();
    this.ISBN = ISBN;
    try {
      JSONObject bookDetails = googleBooksService.getBookDetails(ISBN);
      this.tittle = bookDetails.getString("title");
      this.subject = bookDetails.optString("categories", "Unknown");
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

  public Set<Author> getAuthors() {
    return authors;
  }
  
  public String authorsToString() {
    StringBuilder authorsString = new StringBuilder();
    for (Author author : authors) {
      authorsString.append(author.getName()).append(", ");
    }
    return authorsString.toString();
  }

}
