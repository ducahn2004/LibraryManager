package org.group4.base.Database;

import java.util.ArrayList;
import java.util.List;

import org.group4.base.users.Member;
import org.group4.base.entities.Person;

public class MemberDatabase {
    private final static List<Member> members = new ArrayList<>();

    static {
        // Add members to the database
        Person NguyenDogAhn = new Person("Nguyen Dog Ahn", "22022171@vnu.edu.vn");
        members.add(new Member("22022171", "123", NguyenDogAhn));

        Person TranDogAnh = new Person("Tran Dog Anh", "22022189@vnu.edu.vn");
        members.add(new Member("22022189", "123", TranDogAnh));
    }

    public static List<Member> getMembers() {
        return members;
    }
}
