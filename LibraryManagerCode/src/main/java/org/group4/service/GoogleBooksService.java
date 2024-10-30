package org.group4.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleBooksService {

    private static final String API_KEY = "AIzaSyCuR8GXwJhZj52z8KIX7apBkVpUbtGCAzU";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private final OkHttpClient client = new OkHttpClient();

    public List<String> searchBooks(String query) throws IOException {
        List<String> bookTitles = new ArrayList<>();
        String url = BASE_URL + "?q=" + query + "&key=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonArray items = jsonObject.getAsJsonArray("items");

                for (JsonElement item : items) {
                    JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");
                    String title = volumeInfo.get("title").getAsString();
                    bookTitles.add(title);
                }
            }
        }

        return bookTitles;
    }
}