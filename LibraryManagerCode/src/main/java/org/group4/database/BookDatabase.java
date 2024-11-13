package org.group4.database;

import java.util.HashSet;
import java.util.Set;
import org.group4.base.books.Author;
import org.group4.base.books.Book;

public class BookDatabase extends Database<Book> {
  private static final BookDatabase instance = new BookDatabase();

  private BookDatabase() {
    initializeBooks(); // Call this method to initialize books in the database
  }

  public static BookDatabase getInstance() {
    return instance;
  }
  private void initializeBooks() {
    // Create authors
    Author author1 = new Author("J.K. Rowling");
    Author author2 = new Author("George Orwell");
    Author author3 = new Author("Harper Lee");
    Author author4 = new Author("Jane Austen");
    Author author5 = new Author("F. Scott Fitzgerald");

    // Create a set of authors for each book
    Set<Author> authors1 = new HashSet<>();
    authors1.add(author1);

    Set<Author> authors2 = new HashSet<>();
    authors2.add(author2);

    Set<Author> authors3 = new HashSet<>();
    authors3.add(author3);

    Set<Author> authors4 = new HashSet<>();
    authors4.add(author4);

    Set<Author> authors5 = new HashSet<>();
    authors5.add(author5);

    // Create and add books
    Book book1 = new Book("978-0-7475-3269-9", "Harry Potter and the Philosopher's Stone", "Fantasy", "Bloomsbury", "English", 223, authors1);
    Book book2 = new Book("978-0-452-28423-4", "1984", "Dystopian", "Plume", "English", 328, authors2);
    Book book3 = new Book("978-0-06-112008-4", "To Kill a Mockingbird", "Southern Gothic", "J.B. Lippincott & Co.", "English", 281, authors3);
    Book book4 = new Book("978-0-19-283355-4", "Pride and Prejudice", "Romance", "Oxford University Press", "English", 279, authors4);
    Book book5 = new Book("978-0-7432-7356-5", "The Great Gatsby", "Novel", "Scribner", "English", 180, authors5);

    // Add books to the database
    addItem(book1);
    addItem(book2);
    addItem(book3);
    addItem(book4);
    addItem(book5);
  }
}