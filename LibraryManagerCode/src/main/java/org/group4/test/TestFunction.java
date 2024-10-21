package org.group4.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.group4.base.books.BookItem;
import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.entities.Person;
import org.group4.base.entities.Address;
import org.group4.base.enums.BookFormat;
import org.group4.base.exceptions.BookNotAvailableException;
import org.group4.base.exceptions.InputFormatException;
import org.group4.base.exceptions.InvalidInputException;
import org.group4.base.exceptions.MissingInputException;
import org.group4.base.users.Librarian;
import org.group4.base.users.Account;
import org.group4.database.AccountDatabase;
import org.group4.database.BookDatabase;
import org.group4.database.BookItemDatabase;

public class TestFunction {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Library Management System");
      System.out.println("1. Register");
      System.out.println("2. Login");
      System.out.println("3. Exit");
      System.out.print("Choose an option: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          // Registration logic
          System.out.print("Name: ");
          String name = scanner.nextLine();

          System.out.print("Email: ");
          String email = scanner.nextLine();

          System.out.print("ID: ");
          String tai_Khoan = scanner.nextLine();

          System.out.print("Password: ");
          String mat_Khau = scanner.nextLine();

          System.out.print("Re-enter Password: ");
          String rePassword = scanner.nextLine();

          System.out.print("City: ");
          String city = scanner.nextLine();

          System.out.print("District: ");
          String district = scanner.nextLine();

          System.out.print("Ward: ");
          String ward = scanner.nextLine();

          System.out.print("Street: ");
          String street = scanner.nextLine();

          System.out.print("Number: ");
          int number = scanner.nextInt();
          scanner.nextLine(); // Consume newline

          Address address = new Address(city, district, ward, street, number);
          Person person = new Person(name, email, address);
          boolean isSuccess = Librarian.register(tai_Khoan, mat_Khau, rePassword, person);
          if (isSuccess) {
            System.out.println("Registration successful!");
          } else {
            System.out.println("Registration failed.");
          }

          AccountDatabase.getInstance().getItems().forEach(acc -> System.out.println(acc.getId()));
          break;
        case 2:
          // Login logic
          System.out.print("ID: ");
          String id = scanner.nextLine();
          System.out.print("Password: ");
          String password = scanner.nextLine();
          boolean isLoginSuccess = Account.login(id, password);
          if (isLoginSuccess) {
            System.out.println("Login successful!");
            try {
              afterLogin();
            } catch (MissingInputException | InputFormatException | InvalidInputException | BookNotAvailableException e) {
              System.err.println("Error: " + e.getMessage());
            }
          } else {
            System.out.println("Login failed.");
          }
          break;
        case 3:
          System.out.println("Exiting...");
          scanner.close();
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  public static void afterLogin() throws MissingInputException, InputFormatException, InvalidInputException, BookNotAvailableException {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Choose an option:");
    System.out.println("1. Add book");
    System.out.println("2. Add book item");
    System.out.println("3. Exit");
    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    switch (choice) {
      case 1:
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
        break;
      case 2:
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        if (isbn.isEmpty()) {
          throw new MissingInputException("ISBN is missing.");
        }
        if (!isbn.matches("\\d{13}")) {
          throw new InputFormatException("ISBN must be a 13-digit number.");
        }
        BookDatabase.getInstance().getItems().forEach(book -> {
          if (book.getISBN().equals(isbn)) {
            System.out.println("Book found: " + book.getTitle());
          } else {
            try {
              throw new BookNotAvailableException("Book not found!");
            } catch (BookNotAvailableException e) {
              throw new RuntimeException(e);
            }
          }
        });

        System.out.print("Enter Barcode: ");
        String barcode = scanner.nextLine();
        if (barcode.isEmpty()) {
          throw new MissingInputException("Barcode is missing.");
        }

        System.out.print("Enter Reference Only: ");
        String referenceOnlyStr = scanner.nextLine();
        if (referenceOnlyStr.isEmpty()) {
          throw new MissingInputException("Reference Only is missing.");
        }
        boolean referenceOnly;
        try {
          referenceOnly = Boolean.parseBoolean(referenceOnlyStr);
        } catch (NumberFormatException e) {
          throw new InputFormatException("Reference Only must be a valid boolean.");
        }

        System.out.print("Enter Price: ");
        String priceStr = scanner.nextLine();
        if (priceStr.isEmpty()) {
          throw new MissingInputException("Price is missing.");
        }
        int price;
        try {
          price = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
          throw new InputFormatException("Price must be a valid number.");
        }

        System.out.print("Enter Format: ");
        String formatStr = scanner.nextLine();
        if (formatStr.isEmpty()) {
          throw new MissingInputException("Format is missing.");
        }
        BookFormat format;
        try {
          format = BookFormat.valueOf(formatStr);
        } catch (IllegalArgumentException e) {
          throw new InvalidInputException("Format must be a valid format.");
        }

        System.out.print("Enter Date of Purchase: ");
        String dateOfPurchaseStr = scanner.nextLine();
        if (dateOfPurchaseStr.isEmpty()) {
          throw new MissingInputException("Date of Purchase is missing.");
        }
        LocalDate dateOfPurchase = LocalDate.parse(dateOfPurchaseStr);


        System.out.print("Publication Date: ");
        String publicationDateStr = scanner.nextLine();
        if (publicationDateStr.isEmpty()) {
          throw new MissingInputException("Publication Date is missing.");
        }
        LocalDate publicationDate = LocalDate.parse(publicationDateStr);

        Book book1 = BookDatabase.getInstance().getItems().getFirst();
        BookItem bookItem = new BookItem(book1.getISBN(), book1.getTitle(), book1.getSubject(),
            book1.getPublisher(), book1.getLanguage(),
            book1.getNumberOfPages(), book1.getAuthors(),
            barcode,
            referenceOnly, price,
            format,
            dateOfPurchase,
            publicationDate);
        BookItemDatabase.getInstance().getItems().add(bookItem);
        break;
      case 3:
        System.out.println("Block member");
        break;
      case 4:
        System.out.println("Unblock member");
      case 5:
        System.out.println("View member details");
      case 6:
        System.out.println("View book details");
      case 7:
        System.out.println("Exiting...");
        scanner.close();
        break;
    }
  }
}