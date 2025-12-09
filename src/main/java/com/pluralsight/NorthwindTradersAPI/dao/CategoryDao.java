package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;
import java.util.*;

public interface CategoryDao {

    List<Category> getAll();

    Category getById(int id);
}
