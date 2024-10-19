package org.group4.base.database;

import org.group4.base.users.Member;
import org.group4.base.entities.Person;

public class MemberDatabase extends Database<Member> {
    private static final MemberDatabase instance = new MemberDatabase();

    private MemberDatabase() {

        Person NguyenDogAhn = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn");
        addItem(new Member("22022171", "123", NguyenDogAhn));

        Person TranDogAnh = new Person("Tran Dog Anh", "22022189@vnu.edu.vn");
        addItem(new Member("22022189", "123", TranDogAnh));
    }

    public static MemberDatabase getInstance() {
        return instance;
    }
}