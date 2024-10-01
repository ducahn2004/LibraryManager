package org.group4.base;

import java.lang.reflect.Member;

public class Librarian extends Account {

  public Librarian(String id, String password, AccountStatus status, Person person) {
    super(id, password, status, person);
  }

  public boolean addBookItem(BookItem bookItem) {
    return true;
  }

  public boolean blockMember(Member member) {
    if (member instanceof Account) {
      ((Account) member).setStatus(AccountStatus.Blacklisted);
      return true;
    }
    return false;
  }

  public boolean unblockMember(Member member) {
    if (member instanceof Account) {
      ((Account) member).setStatus(AccountStatus.Active);
      return true;
    }
    return false;
  }

}
