package org.group4.service.book;

import java.io.IOException;
import java.util.List;
import org.group4.dao.book.BookItemDAO;
import org.group4.dao.base.FactoryDAO;
import org.group4.dao.misc.QRCodeDAO;
import org.group4.model.book.BookItem;
import org.group4.model.enums.NotificationType;
import org.group4.service.interfaces.GenericManagerService;
import org.group4.service.notification.SystemNotificationService;
import org.group4.service.qrcode.QRCodeGenerator;

public class BookItemManagerService implements GenericManagerService<BookItem> {

  private static final BookItemDAO bookItemDAO = FactoryDAO.getBookItemDAO();
  private static final QRCodeDAO qrCodeDAO = FactoryDAO.getQRCodeDAO();
  private final SystemNotificationService systemNotificationService =
      SystemNotificationService.getInstance();

  @Override
  public boolean add(BookItem bookItem) throws IOException {
    if (bookItemDAO.add(bookItem)) {
      String qrPath= QRCodeGenerator.generateQRCodeForBookItem(bookItem);
      qrCodeDAO.addQRCode(bookItem.getBarcode(), qrPath);
      systemNotificationService.sendNotification(NotificationType.ADD_BOOK_ITEM_SUCCESS,
          bookItem.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(BookItem bookItem) {
    if (bookItemDAO.update(bookItem)) {
      systemNotificationService.sendNotification(NotificationType.UPDATE_BOOK_ITEM_SUCCESS,
          bookItem.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String barcode) {
    if (bookItemDAO.delete(barcode)) {
      qrCodeDAO.deleteByBarcode(barcode);
      systemNotificationService.sendNotification(NotificationType.DELETE_BOOK_ITEM_SUCCESS,
          barcode);
      return true;
    }
    return false;
  }

  @Override
  public List<BookItem> getAll() {
    return bookItemDAO.getAll();
  }
}
