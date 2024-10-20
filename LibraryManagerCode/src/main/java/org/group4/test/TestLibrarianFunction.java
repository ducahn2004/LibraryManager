package org.group4.test;

import java.util.ArrayList;

import org.group4.base.users.Librarian;
import org.group4.base.entities.Person;
import org.group4.base.entities.Book;
import org.group4.base.books.BookItem;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;
import org.group4.base.users.Member;
import org.group4.database.BookDatabase;
import org.group4.database.BookItemDatabase;
import org.group4.database.AccountDatabase;
import org.group4.base.entities.Address;

import java.time.LocalDate;

public class TestLibrarianFunction {
    public static void main(String[] args) {
        testAddBook();
        testViewBookDetails();
        testAddBookItem();
        testViewBookItemDetails();
        testRemoveBookItem();
        testBlockMember();
        testUnblockMember();
        testViewMemberDetails();
    }

    public static void testAddBook() {
        Address address = new Address("Thai Binh", "Thai Thuy", "Thai Nam", "Chiu", 123);
        Person librarianPerson = new Person("Librarian", "librarian@example.com", address);
        Librarian librarian = new Librarian("lib001", "password", librarianPerson);

        Book book = new Book("2222222222", "Book", "Author", "Publisher",
            "Language", 100, new ArrayList<>());
        librarian.addBook(book);

        System.out.println("Book added: " + BookDatabase.getInstance().getItems().contains(book));
    }

    public static void testViewBookDetails() {
        Book book = BookDatabase.getInstance().getItems().getFirst();
        book.printDetails();
    }

    public static void testAddBookItem() {
        Address address = new Address("Thai Binh", "Thai Thuy", "Thai Nam", "Chiu", 123);
        Person librarianPerson = new Person("Librarian", "librarian@example.com", address);
        Librarian librarian = new Librarian("lib001", "password", librarianPerson);

        BookItem bookItem = new BookItem("2222222222", "Book", "Author", "Publisher",
            "Language", 100, new ArrayList<>(), "11111", false,
            LocalDate.now(), LocalDate.now().plusDays(14), 50000, BookFormat.PAPERBACK,
            BookStatus.AVAILABLE, LocalDate.now().minusDays(20), LocalDate.now().minusMonths(5));
        librarian.addBookItem(bookItem);

        System.out.println("BookItem added: " + BookItemDatabase.getInstance().getItems().contains(bookItem));
    }

    public static void testViewBookItemDetails() {
        BookItem bookItem = BookItemDatabase.getInstance().getItems().getFirst();
        bookItem.printDetails();
    }

    public static void testRemoveBookItem() {
        Address address = new Address("Thai Binh", "Thai Thuy", "Thai Nam", "Chiu", 123);
        Person librarianPerson = new Person("Librarian", "librarian@example.com", address);
        Librarian librarian = new Librarian("lib001", "password", librarianPerson);

        BookItem bookItem = BookItemDatabase.getInstance().getItems().getFirst();
        librarian.removeBookItem(bookItem);

        System.out.println("BookItem removed: " + !BookItemDatabase.getInstance().getItems().contains(bookItem));
    }

    public static void testBlockMember() {
        Address address = new Address("Thai Binh", "Thai Thuy", "Thai Nam", "Chiu", 123);
        Person librarianPerson = new Person("Librarian", "librarian@example.com", address);
        Librarian librarian = new Librarian("lib001", "password", librarianPerson);

        boolean result = librarian.blockMember("22022171");
        System.out.println("Member blocked: " + result);
    }

    public static void testUnblockMember() {
        Address address = new Address("Thai Binh", "Thai Thuy", "Thai Nam", "Chiu", 123);
        Person librarianPerson = new Person("Librarian", "librarian@example.com", address);
        Librarian librarian = new Librarian("lib001", "password", librarianPerson);

        boolean result = librarian.unblockMember("22022171");
        System.out.println("Member unblocked: " + result);
    }

    public static void testViewMemberDetails() {
        Member member = (Member) AccountDatabase.getInstance().getItems().getFirst();
        member.printDetails();
    }
}