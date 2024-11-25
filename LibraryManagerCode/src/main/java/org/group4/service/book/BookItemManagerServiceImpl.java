package org.group4.service.book;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import java.util.Optional;
import org.group4.dao.book.BookItemDAO;
import org.group4.dao.base.FactoryDAO;
import org.group4.dao.misc.QRCodeDAO;
import org.group4.model.book.BookItem;
import org.group4.model.enums.NotificationType;
import org.group4.service.interfaces.BookItemManagerService;
import org.group4.service.notification.SystemNotificationService;
import org.group4.service.qrcode.QRCodeGenerator;

public class BookItemManagerServiceImpl implements BookItemManagerService {

  private static final BookItemDAO bookItemDAO = FactoryDAO.getBookItemDAO();
  private static final QRCodeDAO qrCodeDAO = FactoryDAO.getQRCodeDAO();
  private final SystemNotificationService systemNotificationService =
      SystemNotificationService.getInstance();

  @Override
  public boolean add(BookItem bookItem) throws IOException, SQLException {
    if (bookItemDAO.add(bookItem)) {

      // Generate QR code for book item
      String qrPath= QRCodeGenerator.generateQRCodeForBookItem(bookItem);
      if (!qrCodeDAO.addQRCode(bookItem.getBarcode(), qrPath)) {
        return false;
      }

      // Send notification
      systemNotificationService.sendNotification(NotificationType.ADD_BOOK_ITEM_SUCCESS,
          bookItem.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(BookItem bookItem) throws SQLException {
    if (bookItemDAO.update(bookItem)) {
      systemNotificationService.sendNotification(NotificationType.UPDATE_BOOK_ITEM_SUCCESS,
          bookItem.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String barcode) throws SQLException {
    if (bookItemDAO.delete(barcode)) {
      Optional<String> filePath = qrCodeDAO.getByBarcode(barcode);
      if (filePath.isPresent()) {
        File file = new File(filePath.get());
        if (file.delete()) { // Delete QR code image
          if (!qrCodeDAO.deleteByBarcode(barcode)) { // Delete QR code from database
            return false;
          }
        }
      }

      // Send notification
      systemNotificationService.sendNotification(NotificationType.DELETE_BOOK_ITEM_SUCCESS,
          barcode);
      return true;
    }
    return false;
  }

  @Override
  public List<BookItem> getAll() throws SQLException {
    return bookItemDAO.getAll();
  }

  @Override
  public Optional<String> getQRCode(String barcode) throws SQLException {
    return qrCodeDAO.getByBarcode(barcode);
  }
}
