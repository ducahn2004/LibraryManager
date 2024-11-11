package org.group4.base.manager;

import org.group4.base.books.Book;
import org.group4.database.BookDatabase;

public class BookManager implements Manager<Book> {
    @Override
    public boolean add(Book item) {
        return BookDatabase.getInstance().getItems().add(item);
    }

    @Override
    public boolean remove(Book item) {
        return BookDatabase.getInstance().getItems().remove(item);
    }

    @Override
    public boolean update(Book item) {
        return BookDatabase.getInstance().updateItem(item);
    }

}
