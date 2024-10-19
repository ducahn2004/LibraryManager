package org.group4.base.database;

import org.group4.base.entities.Person;
import org.group4.base.users.Librarian;

public class LibrarianDatabase extends Database<Librarian> {
    private static final LibrarianDatabase instance = new LibrarianDatabase();

    private LibrarianDatabase() {
      Person admin = new Person("admin", "admin@gmail.com");
        addItem(new Librarian("admin", "123", admin));
    }

    public static LibrarianDatabase getInstance() {
        return instance;
    }
}