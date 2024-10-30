package org.group4.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleBooksServiceByISBN {

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    public JSONObject getBookDetails(String isbn) throws IOException {
        JSONArray items = fetchItems(isbn);
        if (!items.isEmpty()) {
            return items.getJSONObject(0).getJSONObject("volumeInfo");
        } else {
            throw new IOException("No book found with the given ISBN");
        }
    }

    private JSONArray fetchItems(String isbn) throws IOException {
        URI uri = URI.create(API_URL + isbn);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Failed to fetch book details");
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder inline = new StringBuilder();
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        JSONObject jsonResponse = new JSONObject(inline.toString());
        return jsonResponse.getJSONArray("items");
    }
}