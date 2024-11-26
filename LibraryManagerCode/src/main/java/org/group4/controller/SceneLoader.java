package org.group4.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.group4.model.book.BookItem;

public class SceneLoader {

  /**
   * Loads the book details page for a given book item.
   *
   * @param currentStage   the current stage
   * @param currentBookItem the book item to display details for
   */
  public static void loadBookDetail(Stage currentStage, BookItem currentBookItem) {
    try {
      FXMLLoader loader = new FXMLLoader(
          SceneLoader.class.getResource("BookDetails.fxml"));
      Scene bookDetailScene = new Scene(loader.load());
      currentStage.setScene(bookDetailScene);

      BookDetailsController bookDetailsController = loader.getController();
      bookDetailsController.setItemDetail(currentBookItem);
    } catch (IOException e) {
      showAlert(
          "Failed to load the book details view. Please try again.");
    } catch (Exception e) {
      showAlert(
          "An unexpected error occurred. Please try again.");
    }
  }

  /**
   * Shows an alert dialog with the provided title and content.
   *
   * @param content the content of the alert
   */
  private static void showAlert(String content) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}