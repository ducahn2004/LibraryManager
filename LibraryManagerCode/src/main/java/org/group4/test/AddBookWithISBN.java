package org.group4.test;

import org.group4.base.entities.Author;
import org.group4.base.entities.Book;
import org.group4.base.users.Librarian;
import org.group4.service.GoogleBooksServiceByISBN;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddBookWithISBN {

    private static final Librarian loggedInLibrarian = new Librarian();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        GoogleBooksServiceByISBN googleBooksServiceByISBN = new GoogleBooksServiceByISBN();
        try {
            JSONObject bookDetails = googleBooksServiceByISBN.getBookDetails(isbn);
            String title = bookDetails.getString("title");
            String subject = bookDetails.optString("categories", "Unknown");
            String publisher = bookDetails.optString("publisher", "Unknown");
            String language = bookDetails.optString("language", "Unknown");
            int pageCount = bookDetails.optInt("pageCount", 0);
            List<Author> authors = new ArrayList<>();
            JSONArray authorsArray = bookDetails.optJSONArray("authors");
            if (authorsArray != null) {
                for (int i = 0; i < authorsArray.length(); i++) {
                    authors.add(new Author(authorsArray.getString(i)));
                }
            }

            Book book = new Book(isbn, title, subject, publisher, language, pageCount, authors);
            loggedInLibrarian.addBook(book);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}