package org.group4.database;

import org.group4.base.entities.Person;
import org.group4.base.entities.Address;
import org.group4.base.users.Librarian;

public class LibrarianDatabase extends Database<Librarian> {
    private static final LibrarianDatabase instance = new LibrarianDatabase();

    private LibrarianDatabase() {
      Address address = new Address("Ha Noi", "Cau Giay", "Xuan Thuy", "Het", 144);
      Person admin = new Person("admin", "admin@gmail.com", address);
      addItem(new Librarian("admin", "123", admin));
    }

    public static LibrarianDatabase getInstance() {
        return instance;
    }
}