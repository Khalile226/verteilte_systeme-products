package de.hs_kl.vis.ss24.products.persistence;

import de.hs_kl.vis.ss24.products.controller.dto.Product;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    private static final String DELETE_PRODUCT_SQL = "DELETE FROM Product WHERE product_id = ?";

    public List<Product> getProducts() throws SQLException{

        List<Product> products= new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT p.*, count(u.unit_id) as amount FROM Product p JOIN Unit u ON p.product_id = u.product_id GROUP BY p.product_id")) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getLong("product_id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setLength(resultSet.getDouble("length"));
                product.setWidth(resultSet.getDouble("width"));
                product.setWeight(resultSet.getDouble("weight"));
                product.setAmount(resultSet.getLong("amount"));
                products.add(product);
            }
            if (products.isEmpty()) {
                throw new SQLException("No products found");
            }
            return products;
        }
    }

    public Product getProductById(Long productId) throws SQLException{

        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement loadProductByIdStatement = connection.prepareStatement("SELECT p.*, count(u.unit_id) as amount FROM Product p JOIN Unit u ON p.product_id = u.product_id WHERE p.product_id = ? GROUP BY p.product_id")) {
                loadProductByIdStatement.setLong(1, productId);
                try (ResultSet resultSet = loadProductByIdStatement.executeQuery()) {

                    if (resultSet.next()) {
                        Product product = new Product();
                        product.setProductId(resultSet.getLong("product_id"));
                        product.setName(resultSet.getString("name"));
                        product.setPrice(resultSet.getDouble("price"));
                        product.setLength(resultSet.getDouble("length"));
                        product.setWidth(resultSet.getDouble("width"));
                        product.setWeight(resultSet.getDouble("weight"));
                        product.setAmount(resultSet.getLong("amount"));
                        return product;
                    } else {
                        throw new SQLException("Product not found");
                    }
                }
            }
        }
    }

    public Product updateProductPrice(Long productId, Double price) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Product SET price = ? WHERE product_id = ?")) {
                updateStatement.setDouble(1, price);
                updateStatement.setLong(2, productId);
                int result = updateStatement.executeUpdate();
                if  (result == 1) {
                    Product newProduct = getProductById(productId);
                    return newProduct;
                } else {
                    throw new SQLException("Product not found");
                }
            }
        }
    }

    public void removeProduct(Long productId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
                deleteStatement.setLong(1, productId);
                deleteStatement.executeUpdate();
            }
        }
    }
}
