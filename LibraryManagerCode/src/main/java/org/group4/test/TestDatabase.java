package org.group4.test;


import java.util.HashSet;
import java.util.Set;
import org.group4.dao.AuthorDAO;
import org.group4.dao.BookDAO;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.Author;
import org.group4.module.books.Book;

public class TestDatabase {
  public static void main(String[] args) {
//    Author author = new Author("TranDogAnh");
    AuthorDAO authorDAO = new AuthorDAO();
//    authorDAO.add(author);
    Book book = new Book("9780743273565",
        "The Great Gatsby",
        "F. Scott Fitzgerald",
        "1925",
        "Scribner",
        100,
        new HashSet<>());

    BookDAO bookDAO = FactoryDAO.getBookDAO();
//    bookDAO.add(book);
    bookDAO.delete("9780743273565");
  }
}
