package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ProductsController {
    private List<Product> productList = new ArrayList<>();

    public ProductsController(){
        productList.add(new Product(1, "Salmon", 1, 22.00));
        productList.add(new Product(5, "Potato", 17, 8.50));
    }

    @RequestMapping(path="/products", method=RequestMethod.GET)
    public List<Product> getProductList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double price){

        List<Product> filtered = new ArrayList<>();

        for(Product p : productList){
            boolean match = true;

            if(name != null){
                if(!p.getProductName().equalsIgnoreCase(name)){
                    match = false;
                }
            }
            if(categoryId != null){
                if(p.getCategoryId() != categoryId){
                    match = false;
                }
            }
            if(price != null){
                if(p.getUnitPrice() != price){
                    match = false;
                }
            }

            if(match){
                filtered.add(p);
            }
        }
        return filtered;
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.GET)
    public Product getProductById(@PathVariable int id){
        for (Product p : productList){
            if(p.getProductId() == id){
                return p;
            }
        }
        return null;
    }
}
