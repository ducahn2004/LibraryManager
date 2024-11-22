package org.group4.model.manager;

import java.io.IOException;
import java.util.List;
import org.group4.dao.BookItemDAO;
import org.group4.dao.FactoryDAO;
import org.group4.dao.QRCodeDAO;
import org.group4.model.books.BookItem;
import org.group4.model.enums.NotificationType;
import org.group4.model.notifications.SystemNotification;
import org.group4.model.qrcode.QRCodeGenerator;

public class BookItemManager implements GenericManager<BookItem> {

  /** The BookItem Data Access Object (DAO). */
  private static final BookItemDAO bookItemDAO = FactoryDAO.getBookItemDAO();
  private static final QRCodeDAO qrCodeDAO = FactoryDAO.getQRCodeDAO();

  @Override
  public boolean add(BookItem bookItem) throws IOException {
    if (bookItemDAO.add(bookItem)) {
      String qrPath= QRCodeGenerator.generateQRCodeForBookItem(bookItem);
      qrCodeDAO.addQRCode(bookItem.getBarcode(), qrPath);
      SystemNotification.sendNotification(NotificationType.ADD_BOOK_ITEM_SUCCESS,
          bookItem.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean update(BookItem bookItem) {
    if (bookItemDAO.update(bookItem)) {
      SystemNotification.sendNotification(NotificationType.UPDATE_BOOK_ITEM_SUCCESS,
          bookItem.toString());
      return true;
    }
    return false;
  }

  @Override
  public boolean delete(String barcode) {
    if (bookItemDAO.delete(barcode)) {
      qrCodeDAO.deleteByBarcode(barcode);
      SystemNotification.sendNotification(NotificationType.DELETE_BOOK_ITEM_SUCCESS, barcode);
      return true;
    }
    return false;
  }

  @Override
  public List<BookItem> getAll() {
    return bookItemDAO.getAll();
  }
}
