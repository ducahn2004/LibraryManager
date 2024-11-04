package org.group4.database;

import org.group4.base.books.BookItem;

public class BookItemDatabase extends Database<BookItem> {
    private static final BookItemDatabase instance = new BookItemDatabase();

    private BookItemDatabase() {
    }
    public static BookItemDatabase getInstance() {
        return instance;
    }
}