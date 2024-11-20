package org.group4.test;

import com.google.zxing.NotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import org.group4.dao.FactoryDAO;
import org.group4.module.books.BookItem;
import org.group4.module.qrcode.QRCodeGenerator;
import org.group4.module.qrcode.QRCodeReader;

public class QRCodeTest {
  public static void main(String[] args) throws SQLException, IOException, NotFoundException {
    Optional<BookItem> bookItem = FactoryDAO.getBookItemDAO().getById("9780132350884-0001");
    if (bookItem.isPresent()) {
      QRCodeGenerator.generateQRCodeForBookItem(bookItem.get());
      System.out.println("QR code generated successfully.");

      String filePath = "LibraryManager/LibraryManagerCode/src/main/resources/qrImage"
          + "/9780132350884-0001.png";
      String text = QRCodeReader.readQRCode(filePath);
      System.out.println("QR code read successfully: \n" + text);

    } else {
      System.out.println("Book item not found.");
    }
  }
}