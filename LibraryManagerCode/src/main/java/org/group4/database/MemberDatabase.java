package org.group4.database;

import java.time.LocalDate;
import org.group4.base.users.Member;

public class MemberDatabase extends Database<Member> {

  private static final MemberDatabase instance = new MemberDatabase();

  private MemberDatabase() {
    addItem(new Member("Nguyễn Đức Anh", LocalDate.of(2004, 3, 10), "22022171@vnu.edu.vn",
        "0912345678"));

    addItem(new Member("Nguyễn Tuấn Anh", LocalDate.of(2004, 1, 23), "22022168@vnu.edu.vn",
        "0382828282"));

    addItem(new Member("Trần Đức Anh", LocalDate.of(2004, 9, 11), "22022189@vnu.edu.vn",
        "03123456789"));

    addItem(new Member("Trần Phương Thảo", LocalDate.of(2003, 10, 3), "22021712@vnu.edu.vn",
        "0987654321"));
  }

  public static MemberDatabase getInstance() {
    return instance;
  }

}