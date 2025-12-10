package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao{
    private DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM products";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                int productId = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                int categoryId = rs.getInt("CategoryID");
                double unitPrice = rs.getDouble("UnitPrice");

                productList.add(new Product(productId, productName, categoryId,unitPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT ProductID, ProductName, CategoryID, UnitPrice FROM products " +
                "WHERE ProductID = ?";

        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new Product(
                            rs.getInt("ProductID"),
                            rs.getString("ProductName"),
                            rs.getInt("CategoryID"),
                            rs.getDouble("UnitPrice")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product insert(Product product) {
        String sql = "INSERT INTO products (ProductName, CategoryID, UnitPrice) " +
                "VALUES (?, ? ,?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getCategoryId());
            ps.setDouble(3, product.getUnitPrice());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int productID = keys.getInt(1);
                    Product p = getById(productID);
                    System.out.printf("New product (ProductID: %d) was successfully added", productID);
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int id, Product product) {
        String sql = "UPDATE products SET ProductName = ?, CategoryID =? , UnitPrice = ? " +
                "WHERE ProductID = ?";
        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getCategoryId());
            ps.setDouble(3, product.getUnitPrice());
            ps.setInt(4, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM products WHERE ProductID = ?";

        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
