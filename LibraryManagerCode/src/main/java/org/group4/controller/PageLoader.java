package org.group4.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.group4.model.book.BookItem;

public class PageLoader {

    public static void openReturningBookPage(Stage currentStage, BookItem bookItem, String previousPage) {
        try {
            FXMLLoader loader = new FXMLLoader(PageLoader.class.getResource("/org/group4/view/ReturningBook.fxml"));
            Scene returningBookScene = new Scene(loader.load());
            currentStage.setScene(returningBookScene);

            ReturningBookController controller = loader.getController();
            controller.setItemDetailReturning(bookItem);
            controller.setPreviousPage(previousPage);

            currentStage.setTitle("Book Item Detail");
        } catch (IOException e) {
            showAlert();
        }
    }

    private static void showAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Failed to load the returning book page. Please try again.");
        alert.showAndWait();
    }
}