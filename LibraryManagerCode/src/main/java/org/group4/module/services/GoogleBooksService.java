package org.group4.module.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Service class for fetching book details from the Google Books API.
 * This class provides a method to retrieve book details by ISBN from the API.
 */
public class GoogleBooksService {

    // Base URL for Google Books API
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    /**
     * Retrieves book details by ISBN.
     *
     * @param isbn the ISBN to look up
     * @return an Optional containing the book's details as a JSONObject, or empty if not found
     */
    public Optional<JSONObject> getBookDetails(String isbn) {
        try {
            JSONArray items = fetchItems(isbn);
            // Check if items array is null or empty
            if (items != null && !items.isEmpty()) {
                return Optional.of(items.getJSONObject(0).getJSONObject("volumeInfo"));
            }
        } catch (IOException e) {
            System.out.println("Error fetching book details: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Fetches book items from the Google Books API based on ISBN.
     *
     * @param isbn the ISBN to search for
     * @return a JSONArray containing book items, or an empty array if not found or error occurs
     * @throws IOException if an I/O error occurs when connecting to the API
     */
    private JSONArray fetchItems(String isbn) throws IOException {
        // Create URI and URL objects
        URI uri = URI.create(API_URL + isbn);
        URL url = uri.toURL();

        // Initialize connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        // Check for successful response code (200 OK)
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("No books found or failed to connect to API.");
            return new JSONArray(); // Return an empty array if no items found
        }

        // Read the API response
        StringBuilder responseContent = new StringBuilder();
        try (Scanner scanner = new Scanner(url.openStream())) {
            while (scanner.hasNext()) {
                responseContent.append(scanner.nextLine());
            }
        }

        // Parse the JSON response and retrieve items
        JSONObject jsonResponse = new JSONObject(responseContent.toString());
        return jsonResponse.optJSONArray("items"); // Return items array or null if not found
    }
}
