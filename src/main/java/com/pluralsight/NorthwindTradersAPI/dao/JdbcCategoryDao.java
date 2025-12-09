package com.pluralsight.NorthwindTradersAPI.dao;
import com.pluralsight.NorthwindTradersAPI.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao {
    private DataSource dataSource;
    @Autowired
    public JdbcCategoryDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAll() {
        return List.of();
    }

    @Override
    public Category getById(int id) {
        return null;
    }
}
