package org.group4.database;

import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.enums.BookFormat;

public class BookItemDatabase extends Database<BookItem> {
    private static final BookItemDatabase instance = new BookItemDatabase();

    private BookItemDatabase() {


    }
    public static BookItemDatabase getInstance() {
        return instance;
    }
}