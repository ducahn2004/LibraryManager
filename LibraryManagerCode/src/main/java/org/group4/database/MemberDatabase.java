package org.group4.database;

import org.group4.base.users.Member;
import org.group4.base.entities.Person;
import org.group4.base.entities.Address;

public class MemberDatabase extends Database<Member> {
    private static final MemberDatabase instance = new MemberDatabase();

    private MemberDatabase() {

        Address address = new Address("Hanoi", "Cau Giay", "Xuan Thuy", "Khuat Duy Tien", 123);
        Person NguyenDogAhn = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn", address);
        addItem(new Member("22022171", "123", NguyenDogAhn));

        Person TranDogAnh = new Person("Tran Dog Anh", "22022189@vnu.edu.vn", address);
        addItem(new Member("22022189", "123", TranDogAnh));
    }

    public static MemberDatabase getInstance() {
        return instance;
    }
}