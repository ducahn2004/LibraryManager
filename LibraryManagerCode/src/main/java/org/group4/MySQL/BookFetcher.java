//package org.group4.MySQL;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class BookFetcher {
//
//  public static <HttpGet> JSONObject fetchBookData(String isbn) throws Exception {
//    String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
//
//    try (CloseableHttpClient client = HttpClients.createDefault()) {
//      HttpGet request = new HttpGet(url);
//      try (CloseableHttpResponse response = client.execute(request)) {
//        String jsonResponse = EntityUtils.toString(response.getEntity());
//        JSONObject jsonObject = new JSONObject(jsonResponse);
//
//        if (jsonObject.has("items")) {
//          return jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
//        }
//      }
//    }
//
//    return null;
//  }
//}
//
