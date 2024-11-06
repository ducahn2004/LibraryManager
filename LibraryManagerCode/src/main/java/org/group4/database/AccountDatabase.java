package org.group4.database;

import java.util.logging.Logger;
import org.group4.base.users.Account;

public class AccountDatabase extends Database<Account> {
    private static final AccountDatabase instance = new AccountDatabase();

    private AccountDatabase() {
        try {
            addItem(new Account("admin", "123456"));
        } catch (Exception e) {
            Logger.getLogger(AccountDatabase.class.getName()).severe("Failed to add default account: " + e.getMessage());
        }
    }

    public static AccountDatabase getInstance() {
        return instance;
    }

}