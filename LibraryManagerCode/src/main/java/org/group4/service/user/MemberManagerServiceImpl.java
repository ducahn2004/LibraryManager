package org.group4.service.user;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.group4.dao.base.FactoryDAO;
import org.group4.dao.user.MemberDAO;
import org.group4.model.enums.NotificationType;
import org.group4.model.user.Member;
import org.group4.service.interfaces.MemberManagerService;
import org.group4.service.notification.SystemNotificationService;

public class MemberManagerServiceImpl implements MemberManagerService {

  private static final MemberDAO memberDAO = FactoryDAO.getMemberDAO();
  private final SystemNotificationService systemNotificationService =
      SystemNotificationService.getInstance();

  @Override
  public boolean add(Member member) throws SQLException {
    if (memberDAO.add(member)) {
      systemNotificationService.sendNotification(NotificationType.ADD_MEMBER_SUCCESS, member.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(Member member) throws SQLException {
    if (memberDAO.update(member)) {
      systemNotificationService.sendNotification(NotificationType.UPDATE_MEMBER_SUCCESS,
          member.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String id) throws SQLException {
    if (memberDAO.delete(id)) {
      systemNotificationService.sendNotification(NotificationType.DELETE_MEMBER_SUCCESS, id);
      return true;
    }
    return false;
  }

  @Override
  public Optional<Member> getById(String id) throws SQLException {
    return memberDAO.getById(id);
  }

  @Override
  public List<Member> getAll() throws SQLException {
    return memberDAO.getAll();
  }
}
