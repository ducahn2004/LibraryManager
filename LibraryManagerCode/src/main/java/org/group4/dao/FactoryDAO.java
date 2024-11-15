package org.group4.dao;

/**
 * FactoryDAO class provides methods to retrieve DAO instances for various entities.
 */
public class FactoryDAO {

  /**
   * Returns an instance of AuthorDAO.
   *
   * @return AuthorDAO instance
   */
  public static AuthorDAO getAuthorDAO() {
    return new AuthorDAO();
  }

  /**
   * Returns an instance of BookDAO.
   *
   * @return BookDAO instance
   */
  public static BookDAO getBookDAO() {
    return new BookDAO();
  }

  /**
   * Returns an instance of BookItemDAO.
   *
   * @return BookItemDAO instance
   */
  public static BookItemDAO getBookItemDAO() {
    return new BookItemDAO();
  }

  /**
   * Returns an instance of RackDAO.
   *
   * @return RackDAO instance
   */
  public static RackDAO getRackDAO() {
    return new RackDAO();
  }

  /**
   * Returns an instance of BookLendingDAO.
   *
   * @return BookLendingDAO instance
   */
  public static BookLendingDAO getBookLendingDAO() {
    return new BookLendingDAO();
  }

  /**
   * Returns an instance of LibrarianDAO.
   *
   * @return LibrarianDAO instance
   */
  public static LibrarianDAO getLibrarianDAO() {
    return new LibrarianDAO();
  }

  /**
   * Returns an instance of AccountDAO.
   *
   * @return AccountDAO instance
   */
  public static AccountDAO getAccountDAO() {
    return new AccountDAO();
  }

  /**
   * Returns an instance of MemberDAO.
   *
   * @return MemberDAO instance
   */
  public static MemberDAO getMemberDAO() {
    return new MemberDAO();
  }

  /**
   * Returns an instance of NotificationDAO.
   *
   * @return NotificationDAO instance
   */
  public static NotificationDAO getNotificationDAO() {
    return new NotificationDAO();
  }
}
