package org.group4.database;

import org.group4.base.users.Member;
import org.group4.base.entities.Person;

public class MemberDatabase extends Database<Member> {
    private static final MemberDatabase instance = new MemberDatabase();

    private MemberDatabase() {
        Member member = new Member("22022168", "22022168", new Person("Alice", "22022168@vnu.edu.vn", "012345689"));
        addItem(member);
        Member member1 = new Member("22022181", "22022181", new Person("Bob", "22022181@vnu.edu.vn", "012345689"));
        addItem(member1);
        Member member2= new Member("22022121", "22022121", new Person("Candy", "22022121@vnu.edu.vn", "012345689"));
        addItem(member2);
        Member member3 = new Member("22022102", "22022102", new Person("Dave", "22022102@vnu.edu.vn", "012345689"));
        addItem(member3);
        Member member4 = new Member("22022190", "22022190", new Person("Evan", "22022190@vnu.edu.vn", "012345689"));
        addItem(member4);
        Member member5 = new Member("22022189", "22022189", new Person("Frank", "22022189@vnu.edu.vn", "012345689"));
        addItem(member5);
        Member member6 = new Member("22022167", "22022167", new Person("Hold", "22022167@vnu.edu.vn", "012345689"));
        addItem(member6);
    }
    public static MemberDatabase getInstance() {
        return instance;
    }
}