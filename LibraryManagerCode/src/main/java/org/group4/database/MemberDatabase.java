package org.group4.database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.group4.base.users.Member;

public class MemberDatabase extends Database<Member> {

  private static final MemberDatabase instance = new MemberDatabase();
  private List<Member> members;

  private MemberDatabase() {
    members = new ArrayList<>();
    initializeMembers();
  }

  public static MemberDatabase getInstance() {
    return instance;
  }

  private void initializeMembers() {
    members.add(
        new Member("Alice Nguyen", LocalDate.of(1990, 5, 10), null, "alice@example.com",
            "0123456789"));
    members.add(
        new Member("Bob Tran", LocalDate.of(1985, 8, 23), null, "bob@example.com", "0987654321" ));
    members.add(
        new Member("Charlie Le", LocalDate.of(1992, 12, 15), null, "charlie@example.com",
            "0112233445"));
    members.add(
        new Member("Daisy Pham", LocalDate.of(1988, 3, 18), null, "daisy@example.com", "0223344556" ));
    members.add(
        new Member("Eve Vo", LocalDate.of(1995, 11, 5), null, "eve@example.com", "0334455667" ));
    System.out.println("Members are initialized");
  }

  public List<Member> getAllMembers() {
    return members;
  }
}