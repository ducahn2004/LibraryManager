package org.group4.database;

import org.group4.base.entities.Account;
import org.group4.base.users.Librarian;

public class AccountDatabase extends Database<Account> {
    private static final AccountDatabase instance = new AccountDatabase();

    private AccountDatabase() {
        Librarian admin = new Librarian("admin", "123456");

    }

    public static AccountDatabase getInstance() {
        return instance;
    }

}