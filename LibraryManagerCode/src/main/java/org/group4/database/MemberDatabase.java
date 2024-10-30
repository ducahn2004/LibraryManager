package org.group4.database;

import org.group4.base.users.Member;
import org.group4.base.entities.Person;

public class MemberDatabase extends Database<Member> {
    private static final MemberDatabase instance = new MemberDatabase();

    private MemberDatabase() {
        Member member = new Member("member", "123456", new Person("Member", "Member@gmail.com", "012345689"));
        addItem(member);
    }
    public static MemberDatabase getInstance() {
        return instance;
    }
}