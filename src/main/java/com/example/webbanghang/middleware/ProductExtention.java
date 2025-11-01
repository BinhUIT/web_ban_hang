package com.example.webbanghang.middleware;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.repository.ProductRepository;

//@Component
public class ProductExtention {
    private final ProductRepository productRepo;
    private final Random rand = new Random();
    private final String[] age ={"Child","Young","Middle","Old"};
    private final String[] gender = {"Male","Female"};
    private final String[] shape = {"Rectangle","Triangle","Apple"};
    public ProductExtention(ProductRepository productRepo) {
        this.productRepo = productRepo;
        List<Product> listProduct = productRepo.findAll();
        for(Product p: listProduct) {
            int randAge = rand.nextInt(4);
            int randGender = rand.nextInt(2);
            int randShape = rand.nextInt(3);
            p.setForAge(age[randAge]);
            p.setForGender(gender[randGender]);
            p.setForShape(shape[randShape]);
        }
        productRepo.saveAll(listProduct);

    } 

    
}
