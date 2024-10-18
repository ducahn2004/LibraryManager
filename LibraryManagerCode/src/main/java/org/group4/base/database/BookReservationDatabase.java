package org.group4.base.database;

import java.util.ArrayList;
import java.util.List;

import org.group4.base.books.BookReservation;

public class BookReservationDatabase {
    private static List<BookReservation> bookReservations = new ArrayList<>();

    static {
        // Add book reservations to the database
        bookReservations.add(new BookReservation());
        bookReservations.add(new BookReservation());
    }

    public static List<BookReservation> getBookReservations() {
        return bookReservations;
    }



}
