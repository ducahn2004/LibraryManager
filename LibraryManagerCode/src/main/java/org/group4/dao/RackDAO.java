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
 * RackDAO implements GenericDAO interface for performing CRUD operations on the 'racks' table.
 */
public class RackDAO extends BaseDAO implements GenericDAO<Rack, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(RackDAO.class);

  private static final String ADD_RACK_SQL = "INSERT INTO racks (numberRack, locationIdentifier) VALUES (?, ?)";
  private static final String UPDATE_RACK_SQL = "UPDATE racks SET locationIdentifier = ? WHERE numberRack "
      + "= ?";
  private static final String DELETE_RACK_SQL = "DELETE FROM racks WHERE numberRack = ?";
  private static final String GET_ALL_RACKS_SQL = "SELECT * FROM racks";
  private static final String GET_RACK_BY_ID_SQL = "SELECT * FROM racks WHERE numberRack = ?";

  @Override
  public boolean add(Rack rack) {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(ADD_RACK_SQL)) {
      stmt.setInt(1, rack.getNumberRack());
      stmt.setString(2, rack.getLocationIdentifier());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error adding rack: {}", rack, e);
      return false;
    }
  }

  @Override
  public boolean update(Rack rack) {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(UPDATE_RACK_SQL)) {
      stmt.setString(1, rack.getLocationIdentifier());
      stmt.setInt(2, rack.getNumberRack());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error updating rack: {}", rack, e);
      return false;
    }
  }

  @Override
  public boolean delete(Rack rack) {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(DELETE_RACK_SQL)) {
      stmt.setInt(1, rack.getNumberRack());
      return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error deleting rack: {}", rack, e);
      return false;
    }
  }

  @Override
  public Optional<Rack> getById(Integer numberRack) throws SQLException {
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_RACK_BY_ID_SQL)) {
      stmt.setInt(1, numberRack);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRowToRack(rs));
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public List<Rack> getAll() {
    List<Rack> racks = new ArrayList<>();
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_ALL_RACKS_SQL);
        ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
        racks.add(mapRowToRack(rs));

      }
    } catch (SQLException e) {
      logger.error("Error getting all racks", e);
    }
    return racks;
  }

  private Rack mapRowToRack(ResultSet rs) throws SQLException {
    int numberRack = rs.getInt("numberRack");
    String locationIdentifier = rs.getString("locationIdentifier");
    return new Rack(numberRack, locationIdentifier);
  }

}
