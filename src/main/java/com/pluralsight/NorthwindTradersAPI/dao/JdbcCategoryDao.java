package com.pluralsight.NorthwindTradersAPI.dao;
import com.pluralsight.NorthwindTradersAPI.models.Category;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao {
    private DataSource dataSource;

    @Autowired
    public JdbcCategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName FROM categories";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int categoryId = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");
                categoryList.add(new Category(categoryId, categoryName));
            }
            return categoryList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT CategoryID, CategoryName FROM categories " +
                "WHERE CategoryID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Category(
                            rs.getInt("CategoryID"),
                            rs.getString("CategoryName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

