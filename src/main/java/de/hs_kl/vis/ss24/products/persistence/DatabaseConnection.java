package de.hs_kl.vis.ss24.products.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://143.93.27.103:3306/haes0004_db1";
    private static final String USER = "haes0004_db1";
    private static final String PASSWORD = "Sample12345$"; // Add your password here if needed

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}