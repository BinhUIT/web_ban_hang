package com.example.webbanghang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.model.entity.Category;
import com.example.webbanghang.service.CategoryService;


@RestController
public class CategoryController {
   private CategoryService categoryService;
   public CategoryController(CategoryService categoryService) {
    this.categoryService= categoryService;
   } 
   @GetMapping("/unsecure/parent_categories")
   public List<Category> getParentCategory() {
        return categoryService.findParenCategories();
   }
   
}
