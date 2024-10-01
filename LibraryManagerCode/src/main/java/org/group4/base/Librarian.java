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
    return true;
  }

  public boolean unblockMember(Member member) {
    return true;
  }

}
