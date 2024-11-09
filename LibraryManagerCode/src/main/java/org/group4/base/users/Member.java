package org.group4.base.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.group4.base.books.BookLending;

/**
 * The {@code Member} class represents a library member who can lend books.
 * It includes information like member ID, book lendings, and related operations.
 */
public class Member extends Person {

  // Atomic integer to generate unique member IDs.
  private static final AtomicInteger idCounter = new AtomicInteger(1);

  private final String memberId;
  private final List<BookLending> bookLendings;

  /**
   * Constructs a {@code Member} object with the given details.
   *
   * @param name the name of the member
   * @param dateOfBirth the date of birth of the member
   * @param email the email address of the member
   * @param phoneNumber the phone number of the member
   */
  public Member(String name, LocalDate dateOfBirth, String email, String phoneNumber) {
    super(name, dateOfBirth, email, phoneNumber);
    this.memberId = generateMemberId();
    this.bookLendings = new ArrayList<>();
  }

  /**
   * Generates a unique member ID based on the current year and an incrementing counter.
   * The counter is reset each year.
   *
   * @return the generated member ID
   */
  private String generateMemberId() {
    int currentYear = LocalDate.now().getYear();
    return currentYear + String.format("%04d", idCounter.getAndIncrement());
  }

  /**
   * Returns the member ID.
   *
   * @return the member ID
   */
  public String getMemberId() {
    return memberId;
  }

  /**
   * Returns the list of books currently lent to the member.
   *
   * @return the book lendings
   */
  public List<BookLending> getBookLendings() {
    return bookLendings;
  }

  /**
   * Returns the book lending for a specific book identified by its barcode.
   *
   * @param barcode the barcode of the book
   * @return an {@code Optional} containing the book lending, if found
   */
  public Optional<BookLending> getBookLending(String barcode) {
    return bookLendings.stream()
        .filter(lending -> lending.getBookItem().getBarcode().equals(barcode))
        .findFirst();
  }

  /**
   * Adds a book lending to the member's list of book lendings.
   *
   * @param bookLending the book lending to add
   */
  public void addBookLending(BookLending bookLending) {
    bookLendings.add(bookLending);
  }

  /**
   * Removes a book lending from the member's list of book lendings.
   *
   * @param bookLending the book lending to remove
   */

  public void removeBookLending(BookLending bookLending) {
    bookLendings.remove(bookLending);
  }
}
