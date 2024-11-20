package org.group4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.group4.module.books.Rack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access Object (DAO) class for CRUD operations on the {@link Rack} entity in the database.
 * This class provides methods to add, update, delete, and retrieve racks using JDBC connection.
 * Each method is executed within a try-with-resources statement to ensure proper resource handling.
 */
public class RackDAO extends BaseDAO implements GenericDAO<Rack, Integer> {

  /** The logger for RackDAO. */
  private static final Logger logger = LoggerFactory.getLogger(RackDAO.class);

  /** Column names in the racks table. */
  private static final String COLUMN_NUMBER_RACK = "rackNumber";
  private static final String COLUMN_LOCATION_IDENTIFIER = "locationIdentifier";

  /** SQL statements for CRUD operations on the racks table. */
  private static final String ADD_RACK_SQL =
      "INSERT INTO racks (" + COLUMN_NUMBER_RACK + ", " + COLUMN_LOCATION_IDENTIFIER + ") "
          + "VALUES (?, ?)";

  private static final String UPDATE_RACK_SQL =
      "UPDATE racks SET "+ COLUMN_LOCATION_IDENTIFIER +" = ? WHERE "+ COLUMN_NUMBER_RACK +" = ?";

  private static final String DELETE_RACK_SQL =
      "DELETE FROM racks WHERE "+ COLUMN_NUMBER_RACK +" = ?";

  private static final String GET_ALL_RACKS_SQL = "SELECT * FROM racks";

  private static final String GET_RACK_BY_ID_SQL =
      "SELECT * FROM racks WHERE "+ COLUMN_NUMBER_RACK +" = ?";

  @Override
  public boolean add(Rack rack) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_RACK_SQL)) {
      preparedStatement.setInt(1, rack.getNumberRack());
      preparedStatement.setString(2, rack.getLocationIdentifier());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding rack: {}", rack, e);
      return false;
    }
  }

  @Override
  public boolean update(Rack rack) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RACK_SQL)) {
      preparedStatement.setString(1, rack.getLocationIdentifier());
      preparedStatement.setInt(2, rack.getNumberRack());
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating rack: {}", rack, e);
      return false;
    }
  }

  @Override
  public boolean delete(Integer numberRack) {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RACK_SQL)) {
      preparedStatement.setInt(1, numberRack);
      return preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting rack: {}", numberRack, e);
      return false;
    }
  }

  @Override
  public Optional<Rack> getById(Integer numberRack) throws SQLException {
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_RACK_BY_ID_SQL)) {
      preparedStatement.setInt(1, numberRack);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapRowToRack(resultSet));
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public List<Rack> getAll() {
    List<Rack> racks = new ArrayList<>();
    try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RACKS_SQL);
        ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
        racks.add(mapRowToRack(resultSet));

      }
    } catch (SQLException e) {
      logger.error("Error getting all racks", e);
    }
    return racks;
  }

  /**
   * Maps a row in the {@code ResultSet} to a {@link Rack} object.
   *
   * @param rs the {@code ResultSet} to map
   * @return a {@code Rack} object
   * @throws SQLException if an error occurs while accessing the {@code ResultSet}
   */
  private Rack mapRowToRack(ResultSet rs) throws SQLException {
    int numberRack = rs.getInt(COLUMN_NUMBER_RACK);
    String locationIdentifier = rs.getString(COLUMN_LOCATION_IDENTIFIER);
    return new Rack(numberRack, locationIdentifier);
  }
}
