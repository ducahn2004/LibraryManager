package org.group4.service.user;

import java.util.List;
import org.group4.dao.base.FactoryDAO;
import org.group4.dao.user.MemberDAO;
import org.group4.model.enums.NotificationType;
import org.group4.model.user.Member;
import org.group4.service.interfaces.GenericManagerService;
import org.group4.service.notification.SystemNotificationService;

public class MemberManagerService implements GenericManagerService<Member> {

  private static final MemberDAO memberDAO = FactoryDAO.getMemberDAO();
  private final SystemNotificationService systemNotificationService =
      SystemNotificationService.getInstance();

  @Override
  public boolean add(Member member) {
    if (memberDAO.add(member)) {
      systemNotificationService.sendNotification(NotificationType.ADD_MEMBER_SUCCESS, member.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(Member member) {
    if (memberDAO.update(member)) {
      systemNotificationService.sendNotification(NotificationType.UPDATE_MEMBER_SUCCESS,
          member.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String id) {
    if (memberDAO.delete(id)) {
      systemNotificationService.sendNotification(NotificationType.DELETE_MEMBER_SUCCESS, id);
      return true;
    }
    return false;
  }

  @Override
  public List<Member> getAll() {
    return memberDAO.getAll();
  }
}
