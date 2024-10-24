package org.group4.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.users.Member;
import org.group4.base.entities.Person;
import org.group4.base.books.BookItem;
import org.group4.database.BookDatabase;
import org.group4.database.BookItemDatabase;
import org.group4.database.MemberDatabase;
import org.group4.base.users.Librarian;
import org.group4.database.AccountDatabase;
import org.group4.base.enums.BookFormat;

import java.util.Scanner;
import org.jetbrains.annotations.NotNull;

public class TestRun {
    private static Member loggedInMember = null;
    private static Librarian loggedInLibrarian = null;

    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (loggedInMember == null && loggedInLibrarian == null) {
                System.out.println("1. Member");
                System.out.println("2. Librarian");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                try {
                    switch (choice) {
                        case 1 -> memberMenu(scanner);
                        case 2 -> librarianLogin(scanner);
                        case 3 -> {
                            System.out.println("Exiting...");
                            return;
                        }
                        default -> System.out.println("Invalid option. Please try again.");
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (loggedInMember != null) {
                memberFunctions(scanner);
            } else { // loggedInLibrarian != null
                librarianFunctions(scanner);
            }
        }
    }

    private static void memberMenu (Scanner scanner){
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Forgot Password");
        System.out.println("4. Back");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            switch (choice) {
                case 1 -> login(scanner);
                case 2 -> register(scanner);
                case 3 -> forgotPassword(scanner);
                case 4 -> {
                    loggedInMember = null;
                    loggedInLibrarian = null;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void librarianLogin (Scanner scanner){
        System.out.print("Enter Librarian ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Librarian librarian = AccountDatabase.getInstance().getItems().stream()
                .filter(acc -> acc.getId().equals(id))
                .map(acc -> (Librarian) acc)
                .findFirst()
                .orElse(null);
        if (librarian != null && librarian.getPassword().equals(password)) {
            loggedInLibrarian = librarian;
            System.out.println("Login successful!");
        } else {
            throw new IllegalArgumentException("Invalid ID or Password.");
        }
    }

    private static void memberFunctions (Scanner scanner){
        System.out.println("1. View Member Details");
        System.out.println("2. View Book Details");
        System.out.println("3. Lend Book");
        System.out.println("4. Reserve Book");
        System.out.println("5. Renew Book");
        System.out.println("6. Cancel Reservation");
        System.out.println("7. Return Book");
        System.out.println("8. View Lending History");
        System.out.println("9. View Reservation History");
        System.out.println("10. View All Notifications");
        System.out.println("11. Update Information");
        System.out.println("12. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            switch (choice) {
                case 1 -> loggedInMember.printDetails();
                case 2 -> viewBookDetails(scanner);
                case 3 -> lendBook(scanner);
                case 4 -> reserveBook(scanner);
                case 5 -> renewBook(scanner);
                case 6 -> cancelReservation(scanner);
                case 7 -> returnBook(scanner);
                case 8 -> loggedInMember.viewLendingHistory();
                case 9 -> loggedInMember.viewReservationsHistory();
                case 10 -> loggedInMember.viewNotifications();
                case 11 -> updateInformation(scanner);
                case 12 -> loggedInMember = null;
                default -> System.out.println("Invalid option. Please try again.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void librarianFunctions (Scanner scanner){
        System.out.println("1. Add Book");
        System.out.println("2. Add Book Item");
        System.out.println("3. Remove Book Item");
        System.out.println("4. Block Member");
        System.out.println("5. Unblock Member");
        System.out.println("6. View Member Details");
        System.out.println("7. View All Books");
        System.out.println("8. View All Book Items");
        System.out.println("9. Logout");
        System.out.println("10. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (choice) {
                case 1 -> addBook(scanner);
                case 2 -> addBookItem(scanner);
                case 3 -> removeBookItem(scanner);
                case 4 -> blockMember(scanner);
                case 5 -> unblockMember(scanner);
                case 6 -> viewMemberDetails(scanner);
                case 7 -> viewAllBooks();
                case 8 -> viewAllBookItems(scanner);
                case 9 -> loggedInLibrarian = null;
                case 10 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Member member = Member.fetchMemberDetails(id);
        if (member != null && member.getPassword().equals(password)) {
            loggedInMember = member;
            System.out.println("Login successful!");
        } else {
            throw new IllegalArgumentException("Invalid ID or Password.");
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        validatePhoneNumber(phoneNumber);

        Person person = new Person(name, email, phoneNumber);
        Member member = new Member(id, password, person);
        MemberDatabase.getInstance().addItem(member);
        System.out.println("Registration successful!");
    }

    private static void forgotPassword(@NotNull Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        Member member = Member.fetchMemberDetails(id);
        if (member != null) {
            System.out.print("Enter New Password: ");
            String newPassword = scanner.nextLine();
            member.setPassword(newPassword);
            System.out.println("Password updated successfully!");
        } else {
            throw new IllegalArgumentException("Member not found.");
        }
    }

    private static void viewBookDetails(Scanner scanner) {
        List<BookItem> bookItems = BookItemDatabase.getInstance().getItems();
        for (BookItem bookItem : bookItems) {
            System.out.println("Barcode: " + bookItem.getBarcode());
            System.out.println("Title: " + bookItem.getTitle());
            System.out.println("Author: " + bookItem.getAuthors());
        }
        System.out.print("Enter Book Barcode: ");
        String barcode = scanner.nextLine();
        BookItem bookItem = BookItem.fetchBookItemDetails(barcode);
        if (bookItem != null) {
            bookItem.printDetails();
        } else {
            throw new IllegalArgumentException("Book not found.");
        }
    }

    private static void lendBook(@NotNull Scanner scanner) {
        System.out.print("Enter Book Barcode: ");
        String barcode = scanner.nextLine();
        BookItem bookItem = BookItem.fetchBookItemDetails(barcode);
        if (bookItem != null) {
            try {
                loggedInMember.lendBookItem(bookItem);
                System.out.println("Book lent successfully!");
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Book not found.");
        }
    }

    private static void reserveBook(@NotNull Scanner scanner) {
        System.out.print("Enter Book Barcode: ");
        String barcode = scanner.nextLine();
        BookItem bookItem = BookItem.fetchBookItemDetails(barcode);
        if (bookItem != null) {
            try {
                loggedInMember.reserveBookItem(bookItem);
                System.out.println("Book reserved successfully!");
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Book not found.");
        }
    }

    private static void renewBook(@NotNull Scanner scanner) {
        System.out.print("Enter Book Barcode: ");
        String barcode = scanner.nextLine();
        BookItem bookItem = BookItem.fetchBookItemDetails(barcode);
        if (bookItem != null) {
            try {
                loggedInMember.renewBookItem(bookItem);
                System.out.println("Book renewed successfully!");
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Book not found.");
        }
    }

    private static void cancelReservation(@NotNull Scanner scanner) {
        System.out.print("Enter Book Barcode: ");
        String barcode = scanner.nextLine();
        BookItem bookItem = BookItem.fetchBookItemDetails(barcode);
        if (bookItem != null) {
            try {
                loggedInMember.cancelReservation(bookItem);
                System.out.println("Reservation cancelled successfully!");
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Book not found.");
        }
    }

    private static void returnBook(@NotNull Scanner scanner) {
        System.out.print("Enter Book Barcode: ");
        String barcode = scanner.nextLine();
        BookItem bookItem = BookItem.fetchBookItemDetails(barcode);
        if (bookItem != null) {
            try {
                loggedInMember.returnBookItem(bookItem);
                System.out.println("Book returned successfully!");
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Book not found.");
        }
    }

    private static void updateInformation(@NotNull Scanner scanner) {
        System.out.print("Enter New Name: ");
        String changeName = scanner.nextLine();
        System.out.print("Enter New Email: ");
        String changeEmail = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        validatePhoneNumber(phoneNumber);

        Person person = new Person(changeName, changeEmail, phoneNumber);
        loggedInMember.updateInformation(person);
        System.out.println("Information updated successfully!");
    }

    private static void validatePhoneNumber(@NotNull String phoneNumber) {
        if (!phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }
    }

    private static void addBook(@NotNull Scanner scanner) {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter Publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter Language: ");
        String language = scanner.nextLine();
        System.out.print("Enter Number of Pages: ");
        int numberOfPages = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Authors: ");
        ArrayList<Author> authors = new ArrayList<>();
        while (true) {
            System.out.print("Enter Author Name (or 'done' to finish): ");
            String authorName = scanner.nextLine();
            if (authorName.equalsIgnoreCase("done")) {
                break;
            }
            System.out.print("Enter Author Description: ");
            String authorDescription = scanner.nextLine();
            authors.add(new Author(authorName));
        }
        Book book = new Book(isbn, title, subject, publisher, language, numberOfPages, authors);
        loggedInLibrarian.addBook(book);
        System.out.println("Book added successfully!");
    }

    private static void addBookItem(@NotNull Scanner scanner) {
        System.out.print("Enter Book ISBN: ");
        String isbn = scanner.nextLine();
        Book book = BookDatabase.getInstance().getItems().stream()
                .filter(b -> b.getISBN().equals(isbn))
                .findFirst()
                .orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Book not found.");
        }
        System.out.print("Enter Barcode: ");
        String barcode = scanner.nextLine();
        System.out.print("Is Reference Only? (true/false): ");
        boolean isReferenceOnly = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Format (HARDCOVER, PAPERBACK, AUDIOBOOK, EBOOK,): ");
        BookFormat format = BookFormat.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Enter Date of Purchase (yyyy-MM-dd): ");
        LocalDate dateOfPurchase = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter Publication Date (yyyy-MM-dd): ");
        LocalDate publicationDate = LocalDate.parse(scanner.nextLine());
        BookItem bookItem = new BookItem(book, barcode, isReferenceOnly, price, format, dateOfPurchase, publicationDate);
        loggedInLibrarian.addBookItem(bookItem);
    }

    private static void removeBookItem(@NotNull Scanner scanner) {
        System.out.print("Enter BookItem Barcode: ");
        String barcode = scanner.nextLine();
        loggedInLibrarian.removeBookItem(barcode);
    }

    private static void blockMember(@NotNull Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        if (Librarian.blockMember(id)) {
            System.out.println("Member blocked successfully!");
        } else {
            throw new IllegalArgumentException("Member not found.");
        }
    }

    private static void unblockMember(@NotNull Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        if (loggedInLibrarian.unblockMember(id)) {
            System.out.println("Member unblocked successfully!");
        } else {
            throw new IllegalArgumentException("Member not found.");
        }
    }

    private static void viewMemberDetails(@NotNull Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        loggedInLibrarian.viewMemberDetails(id);
    }

    private static void viewAllBooks() {
        List<Book> books = BookDatabase.getInstance().getItems();
        for (Book book : books) {
            book.printDetails();
        }
    }

    private static void viewAllBookItems(Scanner scanner) {
        System.out.print("Enter Book ISBN: ");
        String isbn = scanner.nextLine();
        List<BookItem> bookItems = BookItemDatabase.getInstance().getItems().stream()
                .filter(item -> item.getISBN().equals(isbn))
                .toList();
        for (BookItem bookItem : bookItems) {
            bookItem.printDetails();
        }
    }

}