package de.hs_kl.vis.ss24.products;

import java.sql.Connection;
import java.sql.SQLException;

import de.hs_kl.vis.ss24.products.persistence.DatabaseConnection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }
}