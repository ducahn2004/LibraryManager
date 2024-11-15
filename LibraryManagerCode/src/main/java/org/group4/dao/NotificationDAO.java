package org.group4.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.group4.module.notifications.Notification;

public class NotificationDAO implements GenericDAO<Notification, String> {

  NotificationDAO() {
  }

  @Override
  public boolean add(Notification entity) {
    return false;
  }

  @Override
  public boolean update(Notification entity) {
    return false;
  }

  @Override
  public boolean delete(Notification entity) {
    return false;
  }

  @Override
  public Optional<Notification> getById(String s) throws SQLException {
    return Optional.empty();
  }

  @Override
  public Collection<Notification> getAll() {
    return List.of();
  }
}
