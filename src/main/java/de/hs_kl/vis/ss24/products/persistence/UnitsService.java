package de.hs_kl.vis.ss24.products.persistence;

import de.hs_kl.vis.ss24.products.controller.dto.BookingPosition;
import de.hs_kl.vis.ss24.products.controller.dto.Unit;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Service
public class UnitsService {
    private static final String SELECT_RANDOM_UNITS_SQL = "SELECT unit_id, product_id, serial_nr FROM Unit WHERE product_id = ? ORDER BY RAND() LIMIT ?";
    private static final String DELETE_UNIT_SQL = "DELETE FROM Unit WHERE unit_id = ?";
    private static final String INSERT_UNIT_SQL = "INSERT INTO Unit (product_id, serial_nr) VALUES (?, ?)";

    public List<Unit> removeUnits(List<BookingPosition> bookingPositions) throws Exception {
        List<Unit> removedUnits = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            for (BookingPosition position : bookingPositions) {
                List<Unit> unitsToRemove = selectRandomUnits(connection, position.getProductId(), position.getAmount());
                removedUnits.addAll(unitsToRemove);
                deleteUnits(connection, unitsToRemove);
            }
        }

        return removedUnits;
    }

    private List<Unit> selectRandomUnits(Connection connection, Long productId, Long amount) throws SQLException {
        List<Unit> units = new ArrayList<>();

        try (PreparedStatement selectStatement = connection.prepareStatement(SELECT_RANDOM_UNITS_SQL)) {
            selectStatement.setLong(1, productId);
            selectStatement.setLong(2, amount);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    Unit unit = new Unit();
                    unit.setUnitId(resultSet.getLong("unit_id"));
                    unit.setProductId(resultSet.getLong("product_id"));
                    unit.setSerialNumber(resultSet.getString("serial_nr"));
                    units.add(unit);
                }
            }
        }

        return units;
    }

    private void deleteUnits(Connection connection, List<Unit> units) throws SQLException {
        try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_UNIT_SQL)) {
            for (Unit unit : units) {
                deleteStatement.setLong(1, unit.getUnitId());
                deleteStatement.executeUpdate();
            }
        }
    }

    public List<Unit> addUnits(List<Unit> units) throws Exception {
        List<Unit> addedUnits = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_UNIT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            for (Unit unit : units) {
                preparedStatement.setLong(1, unit.getProductId()); // Assuming getProductId() returns the product ID
                preparedStatement.setString(2, unit.getSerialNumber());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            unit.setUnitId(generatedKeys.getLong(1));
                        } else {
                            throw new SQLException("Creating unit failed, no ID obtained.");
                        }
                    }
                }
                addedUnits.add(unit);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions properly in your real application
        }
        return addedUnits;
    }
}
