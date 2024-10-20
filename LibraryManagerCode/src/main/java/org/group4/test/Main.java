package org.group4.test;

import java.util.Scanner;
import org.group4.base.entities.Person;
import org.group4.base.entities.Address;
import org.group4.base.users.Librarian;
import org.group4.base.users.Account;
import org.group4.database.AccountDatabase;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Library Management System");
      System.out.println("1. Register");
      System.out.println("2. Login");
      System.out.println("3. Exit");
      System.out.print("Choose an option: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          // Registration logic
          System.out.print("Name: ");
          String name = scanner.nextLine();

          System.out.print("Email: ");
          String email = scanner.nextLine();

          System.out.print("ID: ");
          String id = scanner.nextLine();

          System.out.print("Password: ");
          String password = scanner.nextLine();

          System.out.print("Re-enter Password: ");
          String rePassword = scanner.nextLine();

          System.out.print("City: ");
          String city = scanner.nextLine();

          System.out.print("District: ");
          String district = scanner.nextLine();

          System.out.print("Ward: ");
          String ward = scanner.nextLine();

          System.out.print("Street: ");
          String street = scanner.nextLine();

          System.out.print("Number: ");
          int number = scanner.nextInt();
          scanner.nextLine(); // Consume newline

          Address address = new Address(city, district, ward, street, number);
          Person person = new Person(name, email, address);
          boolean isSuccess = Librarian.register(id, password, rePassword, person);
          if (isSuccess) {
            System.out.println("Registration successful!");
          } else {
            System.out.println("Registration failed.");
          }
          break;
        case 2:
          // Login logic
          System.out.print("ID: ");
          id = scanner.nextLine();
          System.out.print("Password: ");
          password = scanner.nextLine();
          boolean isLoginSuccess = Account.login(id, password);
          if (isLoginSuccess) {
            System.out.println("Login successful!");
            Librarian librarian = AccountDatabase.getInstance().getItems().stream()
                .filter(acc -> acc.getId().equals(id))
                .map(acc -> (Librarian) acc)
                .findFirst()
                .orElse(null);
          } else {
            System.out.println("Login failed.");
          }
          break;
        case 3:
          System.out.println("Exiting...");
          scanner.close();
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}