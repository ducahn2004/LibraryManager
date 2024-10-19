package org.group4.base.database;

import java.time.LocalDate;
import java.util.ArrayList;

import org.group4.base.books.BookItem;
import org.group4.base.enums.BookFormat;
import org.group4.base.enums.BookStatus;

public class BookItemDatabase extends Database<BookItem> {
    private static final BookItemDatabase instance = new BookItemDatabase();

    private BookItemDatabase() {

        BookItem bookItem1 = new BookItem("1234567890", "Book1", "Author1", "Publisher1",
            "Language1", 100, new ArrayList<>(), "11111", false,
            LocalDate.now(), LocalDate.now().plusDays(14), 50000, BookFormat.PAPERBACK,
            BookStatus.AVAILABLE, LocalDate.now().minusDays(20), LocalDate.now().minusMonths(5));
        addItem(bookItem1);

        BookItem bookItem2 = new BookItem("1234567891", "Book2", "Author2", "Publisher2",
            "Language2", 100, new ArrayList<>(), "22222", false,
            LocalDate.now(), LocalDate.now().plusDays(14), 50000, BookFormat.PAPERBACK,
            BookStatus.AVAILABLE, LocalDate.now().minusDays(20), LocalDate.now().minusMonths(5));
        addItem(bookItem2);

        BookItem bookItem3 = new BookItem("1234567892", "Book3", "Author3", "Publisher3",
            "Language3", 100, new ArrayList<>(), "22222", false,
            LocalDate.now(), LocalDate.now().plusDays(14), 50000, BookFormat.PAPERBACK,
            BookStatus.AVAILABLE, LocalDate.now().minusDays(20), LocalDate.now().minusMonths(5));
        addItem(bookItem3);

    }

    public static BookItemDatabase getInstance() {
        return instance;
    }
}