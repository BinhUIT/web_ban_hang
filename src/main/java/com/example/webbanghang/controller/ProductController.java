package com.example.webbanghang.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.esmodels.Products;
import com.example.webbanghang.service.CouponService;
import com.example.webbanghang.service.ProductService;


@RestController
public class ProductController {
    private ProductService productService;
    private CouponService couponService;
    public ProductController(ProductService productService, CouponService couponService) {
        this.productService= productService;
        this.couponService = couponService;
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
       
        if(fromPrice==-1&&toPrice!=-1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid param");
        }
        if(toPrice==-1&&fromPrice!=-1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid param");
        } 
        if(toPrice!=-1&&fromPrice!=-1&&fromPrice>toPrice) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid param");
        }
        Sort sort = order.equalsIgnoreCase("desc") 
        ? Sort.by(sortCriteria).descending() 
        : Sort.by(sortCriteria).ascending();
        Pageable pageable =PageRequest.of(page, size, sort); 
        Page<Product> firstResult=null;
        if(!keyword.equals("")) {
            try {
            List<Products> listProductsElasticSearch = productService.searchByKeyword(keyword);
            List<Integer> listProductId = listProductsElasticSearch.stream().map(item->Integer.valueOf(item.getId())).collect(Collectors.toList());
            firstResult = productService.findByListIds(listProductId, pageable);
            }
            catch(IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Server error");
            }

        }
        if(catIds!=null) {
            Page<Product> newResult = productService.findByListCategory(catIds, pageable); 
            if(firstResult==null) {
                firstResult= newResult;
            } 
            else {
                firstResult= retainTwoPages(firstResult, newResult);
            }
        } 
        if(sizeIds!=null||colorIds!=null) {
            Page<Product> newResult=productService.findBySizeAndColor(sizeIds, colorIds, pageable); 
            if(firstResult==null) {
                firstResult=newResult;
            }
            else {
                firstResult = retainTwoPages(firstResult, newResult);
            }
        }
        if(toPrice!=-1&&fromPrice!=-1) {
            Page<Product> newResult=productService.findByPriceBetween(fromPrice, toPrice, pageable); 
             if(firstResult==null) {
                firstResult=newResult;
            }
            else {
                firstResult = retainTwoPages(firstResult, newResult);
            }
        }
        if(firstResult!=null) {
            return firstResult;
        }
        return productService.findAllEnableProductsByPage(pageable);
    }
    private  <T> Page<T> retainTwoPages(Page<T> page1, Page<T> page2) {
        List<T> list1= new ArrayList<>(page1.getContent());
        List<T> list2= page2.getContent();
        list1.retainAll(list2);
        return new PageImpl<>(list1, page1.getPageable(),list1.size());
    }
    @GetMapping("/unsecure/all_product") 
    public List<Product> getAllProduct() {
        return productService.findAllProducts();
    }
    @GetMapping("/unsecure/search/{keyword}") 
    public List<Products> searchByKeyword(@PathVariable String keyword) {
        try {
            return productService.searchByKeyword(keyword);
        } catch (IOException e) {
            e.printStackTrace();
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    } 
    @GetMapping("/unsecure/get_enable_coupon") 
    public List<Coupon> getAllEnableCoupons() {
        
        return couponService.getAllUsableCoupon();
    }
    
    
}
