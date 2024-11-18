package org.group4.test;


import java.util.HashSet;
import java.util.Set;
import org.group4.dao.AuthorDAO;
import org.group4.dao.BookDAO;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.Author;
import org.group4.module.books.Book;
import org.group4.module.services.AccountService;
import org.group4.module.users.Account;

public class TestDatabase {
  public static void main(String[] args) {
//    Author author = new Author("TranDogAnh");
   String password = Account.hashPassword("password");

    FactoryDAO.getAccountDAO().update(new Account("admin", password));
  }
}
