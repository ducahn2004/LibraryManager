package org.group4.database;

import java.time.LocalDate;
import java.util.ArrayList;

import org.group4.base.books.BookItem;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;

public class BookItemDatabase extends Database<BookItem> {
    private static final BookItemDatabase instance = new BookItemDatabase();

    private BookItemDatabase() {

    }

    public static BookItemDatabase getInstance() {
        return instance;
    }
}