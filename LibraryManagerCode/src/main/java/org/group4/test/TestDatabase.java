package org.group4.test;

import java.time.LocalDate;
import org.group4.dao.AccountDAO;
import org.group4.dao.FactoryDAO;
import org.group4.module.enums.NotificationType;
import org.group4.module.notifications.SystemNotification;
import org.group4.module.services.AccountService;
import org.group4.module.users.Account;
import org.group4.module.users.Member;

public class TestDatabase {
  public static void main(String[] args) {

    SystemNotification.sendNotification(NotificationType.ADD_BOOK_SUCCESS, "String");

  }
}
