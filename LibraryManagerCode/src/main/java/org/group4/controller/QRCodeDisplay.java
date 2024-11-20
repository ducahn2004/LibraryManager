package org.group4.controller;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class QRCodeDisplay extends Application {

  @Override
  public void start(Stage primaryStage) {
    String filePath = "book_qr.png";
    ImageView imageView = new ImageView();

    try {
      Image image = new Image(new FileInputStream(filePath));
      imageView.setImage(image);
    } catch (FileNotFoundException e) {
      Logger.getLogger(QRCodeDisplay.class.getName()).warning("File not found");
    }

    StackPane root = new StackPane(imageView);
    Scene scene = new Scene(root, 400, 400);

    primaryStage.setTitle("QR Code Display");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
  
}