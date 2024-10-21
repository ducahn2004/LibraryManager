// File: org/group4/test/TestRun.java
package org.group4.test;

import java.util.List;
import org.group4.base.users.Member;
import org.group4.base.entities.Person;
import org.group4.base.books.BookItem;
import org.group4.database.BookItemDatabase;
import org.group4.database.MemberDatabase;

import java.util.Scanner;

public class TestRun {
    private static Member loggedInMember = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (loggedInMember == null) {
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Forgot Password");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                try {
                    switch (choice) {
                        case 1 -> login(scanner);
                        case 2 -> register(scanner);
                        case 3 -> forgotPassword(scanner);
                        case 4 -> {
                            System.out.println("Exiting...");
                            return;
                        }
                        default -> System.out.println("Invalid option. Please try again.");
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
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

    private static void forgotPassword(Scanner scanner) {
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

    private static void lendBook(Scanner scanner) {
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

    private static void reserveBook(Scanner scanner) {
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

    private static void renewBook(Scanner scanner) {
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

    private static void cancelReservation(Scanner scanner) {
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

    private static void returnBook(Scanner scanner) {
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

    private static void updateInformation(Scanner scanner) {
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

    private static void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }
    }
}