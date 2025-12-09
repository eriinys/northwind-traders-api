package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import java.util.*;

public interface ProductDao {

    List<Product> getAll();

    Product getById(int id);

    Product insert(Product product);

    void update(int id, Product product);
}
