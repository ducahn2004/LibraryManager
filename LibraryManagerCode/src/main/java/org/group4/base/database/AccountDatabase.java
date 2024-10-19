package org.group4.base.database;

import org.group4.base.entities.Person;
import org.group4.base.users.Account;
import org.group4.base.users.Member;

public class AccountDatabase extends Database<Account> {
    private static final AccountDatabase instance = new AccountDatabase();

    private AccountDatabase() {
        Person NguyenDogAhn = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn");
        addItem(new Member("22022171", "123", NguyenDogAhn));

        Person TranDogAnh = new Person("Tran Dog Anh", "22022189@vnu.edu.vn");
        addItem(new Member("22022189", "123", TranDogAnh));
    }

    public static AccountDatabase getInstance() {
        return instance;
    }

}