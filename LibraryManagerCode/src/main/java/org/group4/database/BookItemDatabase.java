package org.group4.database;

import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.enums.BookFormat;

public class BookItemDatabase extends Database<BookItem> {
    private static final BookItemDatabase instance = new BookItemDatabase();

    private BookItemDatabase() {
        addItem(new BookItem(BookDatabase.getInstance().getItems().getFirst(), false, 100.00,
            BookFormat.PAPERBACK, LocalDate.now(), LocalDate.now().plusMonths(1)));

        addItem(new BookItem(BookDatabase.getInstance().getItems().getFirst(), true, 110.00,
            BookFormat.PAPERBACK, LocalDate.now(), LocalDate.now().plusMonths(3)));

        addItem(new BookItem(BookDatabase.getInstance().getItems().getFirst(), false, 120.00,
            BookFormat.PAPERBACK, LocalDate.now(), LocalDate.now().plusMonths(2)));

        addItem(new BookItem(BookDatabase.getInstance().getItems().getFirst(), true, 130.00,
            BookFormat.PAPERBACK, LocalDate.now(), LocalDate.now().plusMonths(4)));

        addItem(new BookItem(BookDatabase.getInstance().getItems().getFirst(), false, 140.00,
            BookFormat.PAPERBACK, LocalDate.now(), LocalDate.now().plusMonths(5)));

        addItem(new BookItem(BookDatabase.getInstance().getItems().getFirst(), false, 150.00,
            BookFormat.PAPERBACK, LocalDate.now(), LocalDate.now().plusMonths(6)));


    }
    public static BookItemDatabase getInstance() {
        return instance;
    }
}