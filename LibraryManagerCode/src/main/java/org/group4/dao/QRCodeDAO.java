package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QRCodeDAO extends BaseDAO {

  /**
   * The logger for the QRCodeDAO class.
   */
  private static final Logger logger = LoggerFactory.getLogger(QRCodeDAO.class);

  /** The name of the QR code table. */
  private static final String TABLE_NAME = "qr_codes";

  /** The column names of the QR code table. */
  private static final String COLUMN_BARCODE = "barcode";
  private static final String COLUMN_QR_CODE_URL = "qr_code_url";

  /** SQL queries for the QR code table. */
  private static final String ADD_QR_CODE =
      "INSERT INTO " + TABLE_NAME + " ("
          + COLUMN_BARCODE + ", "
          + COLUMN_QR_CODE_URL + ") "
          + "VALUES (?, ?)";

  private static final String GET_BY_BARCODE =
      "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_BARCODE + " = ?";

  private static final String DELETE_BY_BARCODE =
      "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_BARCODE + " = ?";

  /**
   * Adds a QR code URL for a book item with the given barcode.
   *
   * @param barcode the barcode of the book item
   * @param qrCodeUrl the URL of the QR code
   * @return true if the QR code was successfully added
   */
  public boolean addQRCode(String barcode, String qrCodeUrl) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_QR_CODE)) {
      preparedStatement.setString(1, barcode);
      preparedStatement.setString(2, qrCodeUrl);
      return preparedStatement.executeUpdate() > 0;
    } catch (Exception e) {
      logger.error("Failed to add QR code for book item with barcode: {}", barcode, e);
      return false;
    }
  }

  /**
   * Retrieves the QR code URL for a book item with the given barcode.
   *
   * @param barcode the barcode of the book item
   * @return the QR code URL if it exists
   */
  public Optional<String> getByBarcode(String barcode) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_BARCODE)) {
      preparedStatement.setString(1, barcode);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(resultSet.getString(COLUMN_QR_CODE_URL));
        }
      }
    } catch (Exception e) {
      logger.error("Failed to get QR code for book item with barcode: {}", barcode, e);
    }
    return Optional.empty();
  }

  /**
   * Deletes the QR code for a book item with the given barcode.
   *
   * @param barcode the barcode of the book item
   * @return true if the QR code was successfully deleted
   */
  public boolean deleteByBarcode(String barcode) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_BARCODE)) {
      preparedStatement.setString(1, barcode);
      return preparedStatement.executeUpdate() > 0;
    } catch (Exception e) {
      logger.error("Failed to delete QR code for book item with barcode: {}", barcode, e);
      return false;
    }
  }
}

