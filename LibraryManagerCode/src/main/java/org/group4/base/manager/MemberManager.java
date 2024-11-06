package org.group4.base.manager;

import org.group4.base.users.Member;
import org.group4.database.MemberDatabase;

public class MemberManager implements Manager<Member> {
  @Override
  public boolean add(Member member) {
    if (MemberDatabase.getInstance().getItems().stream()
      .noneMatch(existingMember -> existingMember.getMemberId().equals(member.getMemberId()))) {
      MemberDatabase.getInstance().addItem(member);
      return true;
    }
    return false;
  }

  @Override
  public boolean remove(Member member) {
    MemberDatabase.getInstance().removeItem(member);
    return true;
  }

  @Override
  public boolean update(Member member) {
    MemberDatabase.getInstance().updateItem(member);
    return true;
  }

}
