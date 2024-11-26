package org.group4.service.notification;

import java.sql.SQLException;
import java.util.List;
import org.group4.dao.base.FactoryDAO;
import org.group4.dao.notification.EmailNotificationDAO;
import org.group4.dao.notification.SystemNotificationDAO;
import org.group4.model.notification.EmailNotification;
import org.group4.model.notification.SystemNotification;
import org.group4.service.interfaces.NotificationManager;

public class NotificationManagerImpl implements NotificationManager {

  SystemNotificationDAO systemNotificationDAO = FactoryDAO.getSystemNotificationDAO();
  EmailNotificationDAO emailNotificationDAO = FactoryDAO.getEmailNotificationDAO();

  @Override
  public List<SystemNotification> getAllSystemNotifications() throws SQLException {
    return systemNotificationDAO.getAll();
  }

  @Override
  public List<EmailNotification> getAllEmailNotifications() throws SQLException {
    return emailNotificationDAO.getAll();
  }
}
