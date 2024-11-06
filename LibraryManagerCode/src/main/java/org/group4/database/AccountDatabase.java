package org.group4.database;

import org.group4.base.users.Account;

public class AccountDatabase extends Database<Account> {
    private static final AccountDatabase instance = new AccountDatabase();

    private AccountDatabase() {

    }

    public static AccountDatabase getInstance() {
        return instance;
    }

}