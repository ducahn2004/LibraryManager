package org.group4.base.database;

import java.util.ArrayList;
import java.util.List;

import org.group4.base.books.BookReservation;

public class BookReservationDatabase {

    private final static List<BookReservation> bookReservations = new ArrayList<>();

    public static List<BookReservation> getBookReservations() {
        return bookReservations;
    }
}
