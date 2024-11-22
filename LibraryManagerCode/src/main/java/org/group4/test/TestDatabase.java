package org.group4.test;

import org.group4.service.AccountService;

public class TestDatabase {
  public static void main(String[] args) {

    AccountService accountService = new AccountService();
    accountService.changePassword("admin", "12345", "12");
//    String password = Account.hashPassword("12345");
//    FactoryDAO.getAccountDAO().update(new Account("admin", password));
  }
}
