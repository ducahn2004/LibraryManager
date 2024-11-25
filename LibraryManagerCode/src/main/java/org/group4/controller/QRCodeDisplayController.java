package org.group4.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class QRCodeDisplayController {

  @FXML
  private ImageView qrImageView;

  public void setQRCodeImage(String qrFilePath) {
    File qrFile = new File(qrFilePath);
    if (qrFile.exists()) {
      qrImageView.setImage(new Image(qrFile.toURI().toString()));
    }
  }
}
