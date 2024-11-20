package org.group4.module.manager;

import org.group4.dao.FactoryDAO;
import org.group4.dao.MemberDAO;
import org.group4.module.enums.NotificationType;
import org.group4.module.notifications.SystemNotification;
import org.group4.module.users.Member;

public class MemberManager implements GenericManager<Member> {

  /** The Member Data Access Object (DAO). */
  private static final MemberDAO memberDAO = FactoryDAO.getMemberDAO();

  @Override
  public boolean add(Member member) {
    if (memberDAO.add(member)) {
      SystemNotification.sendNotification(NotificationType.ADD_MEMBER_SUCCESS, member.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(Member member) {
    if (memberDAO.update(member)) {
      SystemNotification.sendNotification(NotificationType.UPDATE_MEMBER_SUCCESS, member.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String id) {
    if (memberDAO.delete(id)) {
      SystemNotification.sendNotification(NotificationType.DELETE_MEMBER_SUCCESS, id);
      return true;
    }
    return false;
  }
}
