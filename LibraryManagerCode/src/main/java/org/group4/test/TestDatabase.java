package org.group4.test;

import java.time.LocalDate;
import org.group4.dao.AccountDAO;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.Rack;
import org.group4.module.enums.NotificationType;
import org.group4.module.notifications.SystemNotification;
import org.group4.module.services.AccountService;
import org.group4.module.users.Account;
import org.group4.module.users.Member;

public class TestDatabase {
  public static void main(String[] args) {

//    SystemNotification.sendNotification(NotificationType.ADD_BOOK_SUCCESS, "String");

    Rack rack1 = new Rack(1, "A1-01");
    Rack rack2 = new Rack(2, "A1-02");
    Rack rack3 = new Rack(3, "A2-01");
    Rack rack4 = new Rack(4, "B1-01");
    Rack rack5 = new Rack(5, "B1-02");
    Rack rack6 = new Rack(6, "C1-01");
    Rack rack7 = new Rack(7, "C1-02");
    Rack rack8 = new Rack(8, "C2-01");
    Rack rack9 = new Rack(9, "C2-02");
    Rack rack10 = new Rack(10, "C2-03");

    FactoryDAO.getRackDAO().add(rack1);
    FactoryDAO.getRackDAO().add(rack2);
    FactoryDAO.getRackDAO().add(rack3);
    FactoryDAO.getRackDAO().add(rack4);
    FactoryDAO.getRackDAO().add(rack5);
    FactoryDAO.getRackDAO().add(rack6);
    FactoryDAO.getRackDAO().add(rack7);
    FactoryDAO.getRackDAO().add(rack8);
    FactoryDAO.getRackDAO().add(rack9);
    FactoryDAO.getRackDAO().add(rack10);
  }
}
