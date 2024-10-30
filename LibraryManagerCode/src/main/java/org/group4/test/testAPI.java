package org.group4.test;

import java.util.Scanner;
import org.group4.service.GoogleBooksService;
import java.io.IOException;
import java.util.List;


public class testAPI {
    public static void main(String[] args) {
      System.out.println("Enter a search query: ");
      String query = new Scanner(System.in).nextLine();
      GoogleBooksService googleBooksService = new GoogleBooksService();
      try {
          List<String> bookTitles = googleBooksService.searchBooks(query);
          for (String title : bookTitles) {
              System.out.println(title);
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
    }
}
