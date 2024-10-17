package org.group4.base.users;

import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;
import org.group4.base.books.BookItem;
import org.group4.base.enums.AccountType;
import org.group4.base.entities.Book;
import org.group4.base.catalog.Rack;

/**
 * Thu thu cua thu vien.
 * Quan ly viec them, sua, xoa sach.
 * Quan ly viec block, unblock tai khoan cua nguoi dung.
 * Co the thay doi thong tin cua tai khoan cua minh.
 * Co the xem thong tin cua sach, nguoi dung.
 * Co the xem thong tin cua cac giao dich.
 * Co the xem thong tin cua cac thong bao.
 * Co the xem thong tin cua cac phieu muon, dat, huy sach.
 */
public class Librarian extends Account {

  /**
   * @param id Ma so cua tai khoan.
   * @param password Mat khau cua tai khoan.
   * @param person Nguoi dung cua tai khoan.
   */
  public Librarian(String id, String password, Person person) {
    super(id, password, person, AccountType.LIBRARIAN, AccountStatus.ACTIVE);
  }

  public void addBook(Book book) {
    // TODO: implement
  }

  public void viewBookDetails(Book book) {
    // TODO: implement
  }

  public boolean addBookItem(BookItem bookItem) {
    // TODO: implement
    return true;
  }

  public void viewBookItemDetails(BookItem bookItem) {
    // TODO: implement
  }

  public boolean removeBookItem(BookItem bookItem) {
    // TODO: implement
    return true;
  }

  public void updateBookItemDetails(BookItem bookItem) {
    // TODO: implement
  }

  public void viewBookItemsOnRack(Rack rack) {
    // TODO: implement
  }

  public boolean blockMember(Member member) {
    if (member != null) {
      member.setStatus(AccountStatus.BLACKLISTED);
      return true;
    }
    return false;
  }

  public boolean unblockMember(Member member) {
    if (member != null) {
      member.setStatus(AccountStatus.ACTIVE);
      return true;
    }
    return false;
  }

  public void viewMemberDetails(Member member) {
    // TODO: implement
  }

}
