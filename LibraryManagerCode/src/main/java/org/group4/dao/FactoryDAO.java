package org.group4.dao;

/**
 * FactoryDAO is a factory class that provides static methods to create instances of DAO classes.
 * It is used to create instances of DAO classes without exposing the instantiation logic to the client.
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
   * Returns an instance of SystemNotificationDAO.
   *
   * @return SystemNotificationDAO instance
   */
  public static SystemNotificationDAO getSystemNotificationDAO() {
    return new SystemNotificationDAO();
  }

  /**
   * Returns an instance of EmailNotificationDAO.
   *
   * @return EmailNotificationDAO instance
   */
  public static EmailNotificationDAO getEmailNotificationDAO() {
    return new EmailNotificationDAO();
  }

  /**
   * Returns an instance of FineDAO.
   *
   * @return FineDAO instance
   */
  public static FineDAO getFineDAO() {
    return new FineDAO();
  }

  /**
   * Returns an instance of QRCodeDAO.
   *
   * @return QRCodeDAO instance
   */
  public static QRCodeDAO getQRCodeDAO() {
    return new QRCodeDAO();
  }
}
