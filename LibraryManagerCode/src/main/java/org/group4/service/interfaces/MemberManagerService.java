package org.group4.service.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.group4.model.user.Member;

/**
 * Interface for managing CRUD operations on {@link Member} entities in the database.
 */
public interface MemberManagerService {

  /**
   * Adds a new member to the database.
   *
   * @param member the member to add
   * @return true if the operation is successful, otherwise false
   * @throws IOException if an error occurs while adding the member
   * @throws SQLException if an error occurs while adding the member
   */
  boolean add(Member member) throws IOException, SQLException;

  /**
   * Updates an existing member in the database.
   *
   * @param member the member to update
   * @return true if the operation is successful, otherwise false
   * @throws SQLException if an error occurs while updating the member
   */
  boolean update(Member member) throws SQLException;

  /**
   * Deletes a member by its ID from the database.
   *
   * @param id the identifier of the member to delete
   * @return true if the operation is successful, otherwise false
   * @throws SQLException if an error occurs while deleting the member
   */
  boolean delete(String id) throws SQLException;

  /**
   * Retrieves all members from the database.
   *
   * @return a list of all members
   * @throws SQLException if an error occurs while retrieving the members
   */
  List<Member> getAll() throws SQLException;
}
