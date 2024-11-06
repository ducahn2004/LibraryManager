package org.group4.database;

import java.io.IOException;

import org.group4.base.books.Book;

public class BookDatabase extends Database<Book> {
    private static final BookDatabase instance;

  static {
    try {
      instance = new BookDatabase();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private BookDatabase() throws IOException {
      addItem(new Book("9781451648546"));
      addItem(new Book("9780385472579"));
      addItem(new Book("9780061122415"));
      addItem(new Book("9780140449136"));
      addItem(new Book("9780262033848"));
      addItem(new Book("9780062316097"));
      addItem(new Book("9780743273565"));
      addItem(new Book("9780307277671"));
      addItem(new Book("9780451524935"));
      addItem(new Book("9780486282114"));
      addItem(new Book("9780679783275"));
      addItem(new Book("9780140449266"));
      addItem(new Book("9781501124020"));
      addItem(new Book("9780307387899"));
      addItem(new Book("9780812981605"));
      addItem(new Book("9780385533225"));
      addItem(new Book("9780385490818"));
      addItem(new Book("9780553380163"));
      addItem(new Book("9780143039433"));
  }

    public static BookDatabase getInstance() {
        return instance;
    }
}