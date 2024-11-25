package org.group4.controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.group4.model.book.BookItem;

public class SceneLoader {

  public static void loadBookDetail(Stage currentStage, BookItem currentBookItem) {
    try {
      FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/org/group4/view/BookDetails.fxml"));
      Scene bookDetailScene = new Scene(loader.load());
      currentStage.setScene(bookDetailScene);

      BookDetailsController bookDetailsController = loader.getController();
      bookDetailsController.setItemDetail(currentBookItem);
    } catch (IOException e) {
      showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the book details view. Please try again.");
    } catch (SQLException e) {
      showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while accessing the database. Please try again.");
    } catch (Exception e) {
      showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred. Please try again.");
    }
  }

  private static void showAlert(AlertType type, String title, String content) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}