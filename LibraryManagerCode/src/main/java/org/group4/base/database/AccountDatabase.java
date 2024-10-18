package org.group4.base.database;

import java.util.ArrayList;
import java.util.List;

import org.group4.base.entities.Person;
import org.group4.base.users.Account;
import org.group4.base.users.Member;

public class AccountDatabase {
    private final static List<Account> accounts = new ArrayList<>();

    static {
        // Add accounts to the database
        Person NguyenDogAhn = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn");
        accounts.add(new Member("22022171", "123", NguyenDogAhn)); // Add Member instance
    }

    public static List<Account> getAccounts() {
        return accounts;
    }
}