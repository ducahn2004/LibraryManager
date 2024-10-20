package org.group4.database;

import org.group4.base.entities.Person;
import org.group4.base.users.Account;
import org.group4.base.users.Member;
import org.group4.base.entities.Address;

public class AccountDatabase extends Database<Account> {
    private static final AccountDatabase instance = new AccountDatabase();

    private AccountDatabase() {

        Address address1 = new Address("Ha Noi", "Cau Giay", "Xuan Thuy", "Khuat Duy Tien", 144);
        Person NguyenDogAhn = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn", address1);
        addItem(new Member("22022171", "123", NguyenDogAhn));

        Person TranDogAnh = new Person("Tran Dog Anh", "22022189@vnu.edu.vn", address1);
        addItem(new Member("22022189", "123", TranDogAnh));
    }

    public static AccountDatabase getInstance() {
        return instance;
    }

}