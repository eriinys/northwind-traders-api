package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.dao.ProductDao;
import com.pluralsight.NorthwindTradersAPI.models.Category;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class ProductsController {
    private ProductDao productDao;

    public ProductsController(ProductDao productDao){
        this.productDao = productDao;
    }

    @RequestMapping(path="/products", method=RequestMethod.GET)
    public List<Product> getProductList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double price){

        List<Product> productList = new ArrayList<>();

        for(Product p : productDao.getAll()){
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
                productList.add(p);
            }
        }
        return productList;
    }

    @RequestMapping(path="/products/{id}", method=RequestMethod.GET)
    public Product getProductById(@PathVariable int id){
        return productDao.getById(id);
    }

    @RequestMapping(path="/products", method=RequestMethod.POST)
    public Product addProduct(@RequestBody Product product){
        return productDao.insert(product);
    }

    @RequestMapping(path="/products/{id}")
    public void updateProduct(
            @PathVariable int id,
            @RequestBody Product product
    ){
        productDao.update(id, product);
    }
}
