package org.group4.base.database;

import java.util.ArrayList;
import org.group4.base.entities.Book;

public class BookDatabase extends Database<Book> {
    private static final BookDatabase instance = new BookDatabase();

    private BookDatabase() {

        Book book1 = new Book("1234567890", "Book1", "Author1", "Publisher1",
            "Language1", 100, new ArrayList<>());
        addItem(book1);

        Book book2 = new Book("1234567891", "Book2", "Author2", "Publisher2",
            "Language2", 100, new ArrayList<>());
        addItem(book2);
    }

    public static BookDatabase getInstance() {
        return instance;
    }
}