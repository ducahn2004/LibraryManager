package org.group4.base.users;

import org.group4.base.entities.Person;
import org.group4.base.enums.AccountStatus;
import org.group4.base.books.BookItem;
import org.group4.base.entities.Book;
import org.group4.base.catalog.Rack;

public class Librarian extends Account {

  public Librarian(String id, String password, Person person) {
    super(id, password, person);
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
    System.out.println("Member ID: " + member.getId());
    System.out.println("Member name: " + member.getPerson().getName());
    System.out.println("Member email: " + member.getPerson().getEmail());
    System.out.println("Member date of membership: " + member.getDateOfMembership());
    System.out.println("Member total books checked out: " + member.getTotalBooksCheckedOut());
  }

}
