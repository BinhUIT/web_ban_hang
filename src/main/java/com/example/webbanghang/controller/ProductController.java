package com.example.webbanghang.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.esmodels.Products;
import com.example.webbanghang.model.response.DetectResult;
import com.example.webbanghang.model.response.Response;
import com.example.webbanghang.service.productservice.ProductFilterService;
import com.example.webbanghang.service.productservice.ProductQueryService;
import com.example.webbanghang.service.productservice.ProductSearchService;
import com.example.webbanghang.service.productservice.ProductService;
import com.example.webbanghang.service.productservice.ProductVariantService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.webbanghang.model.selectcolumninterface.ProductMiniInfo;
import com.example.webbanghang.service.productservice.ProductRecommendationService;



@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductSearchService productSearchService;
    private final ProductQueryService productQueryService;
    private final ProductVariantService productVariantService;
    private final ProductFilterService productFilterService;
    private final ProductRecommendationService productRecommendationService;
    public ProductController(ProductService productService, ProductSearchService productSearchService, ProductQueryService productQueryService, ProductVariantService productVariantService,
     ProductFilterService productFilterService, ProductRecommendationService productRecommendationService) {
        this.productService= productService;
        this.productSearchService = productSearchService;
        this.productQueryService= productQueryService;
        this.productVariantService = productVariantService;
        this.productFilterService = productFilterService;
        this.productRecommendationService = productRecommendationService;
    }
    @GetMapping("/unsecure/product/{id}")
    public Product getProductById(@PathVariable int id) {
        Product product= productService.getById(id);
        if(product==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found");
        }
        return product;
    }
   
    
    @GetMapping("/unsecure/all_enable_products") 
    public Page<Product> getAllEnableProductByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size,
    @RequestParam(defaultValue = "id") String sortCriteria, @RequestParam(defaultValue = "asc") String order, @RequestParam(required = false) List<Long> catIds, @RequestParam(defaultValue="") String catName,
    @RequestParam(defaultValue="-1") int fromPrice, @RequestParam(defaultValue="-1") int toPrice, @RequestParam(required = false) List<Integer> sizeIds,  @RequestParam(required = false) List<Integer> colorIds,
    @RequestParam(defaultValue="") String keyword) { 
       try {
        return productFilterService.filterProduct(page, size, sortCriteria, order, catIds, catName, fromPrice, toPrice, sizeIds, colorIds, keyword);
       }
       catch(Exception e) {
        return null;
       }
        
    }

    @PostMapping("/unsecure/analyze_image")
    public List<ProductMiniInfo> recommendFromImage(@RequestParam("file") MultipartFile file) {
        try { 
            return productRecommendationService.analyzeImage(file);
        }
        catch(IOException e) {
            throw new RuntimeException("Error");
        }
    }
    
    
    @GetMapping("/unsecure/all_product") 
    public List<Product> getAllProduct() {
        return productService.findAllProducts();
    }
    @GetMapping("/unsecure/search/{keyword}") 
    public List<Products> searchByKeyword(@PathVariable String keyword) {
        try {
            return productSearchService.searchByKeyword(keyword);
        } catch (IOException e) {
            e.printStackTrace();
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    } 
    
    @GetMapping("/unsecure/find_variant/{variantId}") 
    public ResponseEntity<Response> getProductVariantById(@PathVariable int variantId) {
        ProductVariant variant = productVariantService.getVariantById(variantId);
        if(variant==null) {
            return new ResponseEntity<>(new Response("Variant Not Found",null,404),HttpStatus.NOT_FOUND);
        } 
        return new ResponseEntity<>(new Response("OK",variant,200), HttpStatus.OK);
    }
    
}
