package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.models.Category;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class CategoriesController {
    private List<Category> categoryList = new ArrayList<>();

    public  CategoriesController(){
        categoryList.add(new Category(1, "Seafood"));
        categoryList.add(new Category(17, "Vegetable"));
    }

    @RequestMapping(path="/categories", method= RequestMethod.GET)
    public List<Category> getCategories(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String name
    ){
        List<Category> filtered = new ArrayList<>();

        for(Category c : categoryList){
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
                filtered.add(c);
            }
        }
        return filtered;
    }

    @RequestMapping(path="/categories/{id}")
    public Category getCategoryById(@PathVariable int id){
        for (Category c : categoryList){
            if(c.getCategoryId() == id){
                return c;
            }
        }
        return null;
    }
}
