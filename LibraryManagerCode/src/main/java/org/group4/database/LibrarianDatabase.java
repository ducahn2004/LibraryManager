package org.group4.database;

import java.time.LocalDate;
import org.group4.base.manager.BookManager;
import org.group4.base.manager.LendingManager;
import org.group4.base.manager.MemberManager;
import org.group4.base.users.Account;
import org.group4.base.users.Librarian;

public class LibrarianDatabase extends Database<Librarian> {
    private static final LibrarianDatabase instance = new LibrarianDatabase();

    private LibrarianDatabase() {
      Account account = new Account("admin", "123456");
      BookManager bookManager = BookManager.getInstance();
      MemberManager memberManager = MemberManager.getInstance();
      LendingManager lendingManager = LendingManager.getInstance();
      addItem(new Librarian("Librarian 1", LocalDate.now(), null, "admin@gmail.com",
          "0382825137", "admin", "123456", bookManager, memberManager, lendingManager));
    }

    public static LibrarianDatabase getInstance() {
        return instance;
    }

}
