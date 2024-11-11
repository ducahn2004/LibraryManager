package org.group4.database;

import java.time.LocalDate;
import org.group4.base.manager.BookItemManager;
import org.group4.base.manager.BookManager;
import org.group4.base.manager.LendingManager;
import org.group4.base.manager.MemberManager;
import org.group4.base.users.Librarian;

public class LibrarianDatabase extends Database<Librarian> {
    private static final LibrarianDatabase instance = new LibrarianDatabase();

    private LibrarianDatabase() {
      BookManager bookManager = new BookManager();
      BookItemManager bookItemManager = new BookItemManager();
      MemberManager memberManager = new MemberManager();
      LendingManager lendingManager = new LendingManager();
      addItem(new Librarian("Librarian 1", LocalDate.now(), "admin@gmail.com",
          "0382825137", "admin", "123456", bookManager, bookItemManager, memberManager, lendingManager));
    }

    public static LibrarianDatabase getInstance() {
        return instance;
    }

}
