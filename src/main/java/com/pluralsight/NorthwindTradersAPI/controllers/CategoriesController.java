package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.dao.CategoryDao;
import com.pluralsight.NorthwindTradersAPI.models.Category;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class CategoriesController {
    private CategoryDao categoryDao;

    public  CategoriesController(CategoryDao categoryDao){
        this.categoryDao = categoryDao;
    }

    @RequestMapping(path="/categories", method= RequestMethod.GET)
    public List<Category> getCategories(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String name
    ){
        List<Category> categoryList = new ArrayList<>();

        for(Category c : categoryDao.getAll()){
            boolean match = true;

            if(categoryId != null){
                if(c.getCategoryId() != categoryId){
                    match =false;
                }
            }
            if(name != null){
                if(!c.getCategoryName().equalsIgnoreCase(name)){
                    match =false;
                }
            }
            if(match){
                categoryList.add(c);
            }
        }
        return categoryList;
    }

    @RequestMapping(path="/categories/{id}")
    public Category getCategoryById(@PathVariable int id){
        return categoryDao.getById(id);
    }
}
