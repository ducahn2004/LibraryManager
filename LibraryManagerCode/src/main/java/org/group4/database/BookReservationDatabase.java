package org.group4.database;

import org.group4.base.books.BookReservation;

public class BookReservationDatabase extends Database<BookReservation> {
    private static final BookReservationDatabase instance = new BookReservationDatabase();

    private BookReservationDatabase() {

    }

    public static BookReservationDatabase getInstance() {
        return instance;
    }

}