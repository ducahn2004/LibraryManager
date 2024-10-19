package org.group4.base.database;

import org.group4.base.books.BookLending;

public class BookLendingDatabase extends Database<BookLending> {
    private static final BookLendingDatabase instance = new BookLendingDatabase();

    private BookLendingDatabase() {

    }

    public static BookLendingDatabase getInstance() {
        return instance;
    }

}