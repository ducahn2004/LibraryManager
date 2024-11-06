package org.group4.database;

import org.group4.base.books.BookLending;

public class BookBorrowDatabase extends Database<BookLending> {
    private static final BookBorrowDatabase instance = new BookBorrowDatabase();

    private BookBorrowDatabase() {

    }

    public static BookBorrowDatabase getInstance() {
        return instance;
    }

}