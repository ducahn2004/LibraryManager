package org.group4.test;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import org.group4.base.entities.Author;
import org.group4.base.exceptions.MissingInputException;
import org.group4.base.exceptions.InvalidInputException;
import org.group4.base.exceptions.InputFormatException;
import org.group4.base.entities.Book;
import org.group4.database.BookDatabase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TestException {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Book book = inputBookInfo(scanner);
            BookDatabase.getInstance().addItem(book);
            Book book1 = BookDatabase.getInstance().getItems().get(2);
            book1.printDetails();
        } catch (MissingInputException | InvalidInputException | InputFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @NotNull
    @Contract("_ -> new")
    public static Book inputBookInfo(@NotNull Scanner scanner) throws MissingInputException, InvalidInputException, InputFormatException {

      System.out.print("Enter book ISBN: ");
      String ISBN = scanner.nextLine();
      if (ISBN.isEmpty()) {
        throw new MissingInputException("ISBN is missing.");
      }
      if (!ISBN.matches("\\d{13}")) {
        throw new InputFormatException("ISBN must be a 13-digit number.");
      }

      System.out.print("Title: ");
      String title = scanner.nextLine();
      if (title.isEmpty()) {
          throw new MissingInputException("Title is missing.");
      }

      System.out.print("Subject: ");
      String subject = scanner.nextLine();
      if (subject.isEmpty()) {
          throw new MissingInputException("Subject is missing.");
      }

      System.out.print("Publisher: ");
      String publisher = scanner.nextLine();
      if (publisher.isEmpty()) {
          throw new MissingInputException("Publisher is missing.");
      }

      System.out.print("Language: ");
      String language = scanner.nextLine();
      if (language.isEmpty()) {
          throw new MissingInputException("Language is missing.");
      }

      System.out.print("Page of number: ");
      String pageOfNumberStr = scanner.nextLine();
      if (pageOfNumberStr.isEmpty()) {
        throw new MissingInputException("Price is missing.");
      }
      int pageOfNumber;
      try {
        pageOfNumber = Integer.parseInt(pageOfNumberStr);
      } catch (NumberFormatException e) {
        throw new InputFormatException("Price must be a valid number.");
      }
      if (pageOfNumber < 0) {
        throw new InvalidInputException("Price cannot be negative.");
      }

      List<Author> authors = new ArrayList<>();
      while (true) {
        System.out.print("Enter author name (or 'done' to finish): ");
        String authorName = scanner.nextLine();
        if (authorName.equalsIgnoreCase("done")) {
          break;
        }
        if (authorName.isEmpty()) {
          throw new MissingInputException("Author name is missing.");
        }

        System.out.print("Enter author description: ");
        String authorDescription = scanner.nextLine();
        if (authorDescription.isEmpty()) {
          throw new MissingInputException("Author description is missing.");
        }

        authors.add(new Author(authorName, authorDescription, new ArrayList<>()));
      }

      return new Book(ISBN, title, subject, publisher, language, pageOfNumber, authors);
    }
}