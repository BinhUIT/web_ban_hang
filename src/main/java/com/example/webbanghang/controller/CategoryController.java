package com.example.webbanghang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.webbanghang.model.entity.Category;
import com.example.webbanghang.service.CategoryService;


@RestController
public class CategoryController {
   private CategoryService categoryService;
   public CategoryController(CategoryService categoryService) {
    this.categoryService= categoryService;
   } 
   @GetMapping("/unsecure/category/{name}") 
   public Category findCatByName(@PathVariable String name) {
     try {
     return categoryService.findByName(name).get(0); 
     }
     catch(Exception e) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Can not find category with name="+name);
     }
   }
   @GetMapping("/unsecure/category") 
   public List<Category> findCategories(@RequestParam(defaultValue="false") boolean isParent,@RequestParam(defaultValue="0") int childrenOf,
   @RequestParam(defaultValue="0") int id, @RequestParam(defaultValue="0") int leavesOf, @RequestParam(defaultValue="") String name ) {
     if(isParent) {
          return categoryService.findParenCategories();
     } 
     if(childrenOf!=0) {
          return categoryService.findByParentId(childrenOf);
     }
     if(id!=0) {
          List<Category> res = new ArrayList<>();
          Category cat = categoryService.findById(id);
          if(cat==null) {
               throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Can not find category with id="+id);
          }
          res.add(cat);
          return res;
     }
     if(leavesOf!=0) {
          Category cat = categoryService.findById(leavesOf); 
          if(cat==null) {
               throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Can not find category with id="+id);
          }
          return categoryService.findLeafCategories(cat);
     }
     if(!name.equals("")) {
          return categoryService.findByName("");
     } 
     return categoryService.findAll();
   }
}
