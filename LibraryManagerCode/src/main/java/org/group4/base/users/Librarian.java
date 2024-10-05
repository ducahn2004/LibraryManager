package org.group4.base.users;

import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;
import org.group4.base.books.BookItem;

public class Librarian extends Account {

  public Librarian(String id, String password, Person person, AccountStatus status) {
    super(id, password, person, status);
  }

  public boolean addBookItem(BookItem bookItem) {
    return true;
  }

  public boolean blockMember(Member member) {
    if (member instanceof Account) {
      ((Account) member).setStatus(AccountStatus.BLACKLISTED);
      return true;
    }
    return false;
  }



  public boolean unblockMember(Member member) {
    if (member instanceof Account) {
      ((Account) member).setStatus(AccountStatus.ACTIVE);
      return true;
    }
    return false;
  }

}
