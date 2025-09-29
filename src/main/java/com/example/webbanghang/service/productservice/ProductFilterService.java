package com.example.webbanghang.service.productservice;

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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.esmodels.Products;

@Service
public class ProductFilterService {
    private final ProductService productService;
    private final ProductQueryService productQueryService;
    private final ProductSearchService productSearchService;
    private final ProductVariantService productVariantService;
    public ProductFilterService(ProductService productService, ProductQueryService productQueryService, ProductSearchService productSearchService, ProductVariantService productVariantService) {
        this.productService= productService;
        this.productQueryService = productQueryService;
        this.productSearchService = productSearchService;
        this.productVariantService = productVariantService;
    }
    public Page<Product> filterProduct(int page, int size, String sortCriteria, String order, List<Long> catIds, String catName, int fromPrice, int toPrice, List<Integer> sizeIds, List<Integer> colorIds, String keyword) throws Exception{
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
            List<Products> listProductsElasticSearch = productSearchService.searchByKeyword(keyword);
            List<Integer> listProductId = listProductsElasticSearch.stream().map(item->Integer.valueOf(item.getId())).collect(Collectors.toList());
            firstResult = productService.findByListIds(listProductId, pageable);
            }
            catch(IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Server error");
            }

        }
        if(catIds!=null) {
            Page<Product> newResult = productQueryService.findByListCategory(catIds, pageable); 
            if(firstResult==null) {
                firstResult= newResult;
            } 
            else {
                firstResult= retainTwoPages(firstResult, newResult);
            }
        } 
        if(sizeIds!=null||colorIds!=null) {
            Page<Product> newResult=productQueryService.findBySizeAndColor(sizeIds, colorIds, pageable); 
            if(firstResult==null) {
                firstResult=newResult;
            }
            else {
                firstResult = retainTwoPages(firstResult, newResult);
            }
        }
        if(toPrice!=-1&&fromPrice!=-1) {
            Page<Product> newResult=productQueryService.findByPriceBetween(fromPrice, toPrice, pageable); 
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
}
