package org.group4.base.manager;

import org.group4.base.users.Member;
import org.group4.database.MemberDatabase;

/**
 * Class for managing operations related to members.
 * Implements the Manager interface to perform CRUD operations.
 */
public class MemberManager implements Manager<Member> {

  /**
   * Adds a new member to the database if not already present.
   *
   * @param member The member to be added.
   * @return true if the member was successfully added, false otherwise.
   */
  @Override
  public boolean add(Member member) {
    // Check if member already exists based on member ID.
    boolean exists = MemberDatabase.getInstance().getItems().stream()
        .anyMatch(existingMember -> existingMember.getMemberId().equals(member.getMemberId()));

    if (!exists) {
      MemberDatabase.getInstance().addItem(member);
      return true;
    }
    return false;
  }

  /**
   * Removes a member from the database.
   *
   * @param member The member to be removed.
   * @return true if the member was successfully removed.
   */
  @Override
  public boolean remove(Member member) {
    // Remove member from the database.
    return MemberDatabase.getInstance().removeItem(member);
  }

  /**
   * Updates the information of an existing member.
   *
   * @param member The member with updated information.
   * @return true if the member was successfully updated.
   */
  @Override
  public boolean update(Member member) {
    // Update member information in the database.
    return MemberDatabase.getInstance().updateItem(member);
  }
}
