package org.group4.database;

import org.group4.base.entities.Person;
import org.group4.base.users.Account;
import org.group4.base.users.Librarian;

public class AccountDatabase extends Database<Account> {
    private static final AccountDatabase instance = new AccountDatabase();

    private AccountDatabase() {
        Librarian admin = new Librarian("admin", "123456", new Person("Librarian", "Librarian@gmail.com", "012345689"));
        addItem(admin);
    }

    public static AccountDatabase getInstance() {
        return instance;
    }

}