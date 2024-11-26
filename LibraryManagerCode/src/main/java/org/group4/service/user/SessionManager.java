package org.group4.service.user;

import org.group4.model.user.Librarian;

/**
 * SessionManager is a singleton class that manages the current session.
 * <p>It stores the current logged-in librarian.</p>
 */
public class SessionManager {

  private static SessionManager instance;
  private Librarian currentLibrarian;

  /** Private constructor to enforce singleton pattern. */
  private SessionManager() {}

  /**
   * Returns the singleton instance of the session manager.
   *
   * @return the singleton instance
   */
  public static synchronized SessionManager getInstance() {
    if (instance == null) {
      instance = new SessionManager();
    }
    return instance;
  }

  /**
   * Sets the current logged-in librarian.
   *
   * @param librarian the logged-in librarian
   */
  public void setCurrentLibrarian(Librarian librarian) {
    this.currentLibrarian = librarian;
  }

  /**
   * Gets the current logged-in librarian.
   *
   * @return the logged-in librarian, or {@code null} if no librarian is logged in
   */
  public Librarian getCurrentLibrarian() {
    return currentLibrarian;
  }

  /**
   * Logs out the current librarian.
   */
  public void logout() {
    this.currentLibrarian = null;
  }
}
