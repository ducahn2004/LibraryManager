package org.group4.database;

import org.group4.base.books.BorrowBookItem;

public class BookBorrowDatabase extends Database<BorrowBookItem> {
    private static final BookBorrowDatabase instance = new BookBorrowDatabase();

    private BookBorrowDatabase() {

    }

    public static BookBorrowDatabase getInstance() {
        return instance;
    }

}