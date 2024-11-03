package org.group4.database;

import java.time.LocalDate;
import org.group4.base.books.BookItem;
import org.group4.base.enums.BookFormat;
import org.group4.base.entities.Book;

public class BookItemDatabase extends Database<BookItem> {
    private static final BookItemDatabase instance = new BookItemDatabase();

    private BookItemDatabase() {
        for (Book book : BookDatabase.getInstance().getItems()) {
            BookItem item1 = new BookItem(book, "BARCODE-001", false, 20.0, BookFormat.HARDCOVER,
                LocalDate.of(2021, 1, 15), LocalDate.of(2020, 5, 20));
            addItem(item1);

            BookItem item2 = new BookItem(book, "BARCODE-002", false, 15.0, BookFormat.PAPERBACK,
                LocalDate.of(2021, 1, 16), LocalDate.of(2020, 5, 21));
            addItem(item2);

            BookItem item3 = new BookItem(book, "BARCODE-003", false, 16.0, BookFormat.AUDIOBOOK,
                LocalDate.of(2021, 3, 10), LocalDate.of(2020, 6, 22));
            addItem(item3);

            BookItem item4 = new BookItem(book, "BARCODE-004", false, 16.0, BookFormat.JOURNAL,
                LocalDate.of(2021, 7, 19), LocalDate.of(2020, 2, 29));
            addItem(item4);

            BookItem item5 = new BookItem(book, "BARCODE-005", false, 16.0, BookFormat.NEWSPAPER,
                LocalDate.of(2019, 6, 30), LocalDate.of(2018, 10, 14));
            addItem(item5);
        }
    }

    public static BookItemDatabase getInstance() {
        return instance;
    }
}