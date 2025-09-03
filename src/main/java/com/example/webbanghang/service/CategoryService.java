package com.example.webbanghang.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Category;
import com.example.webbanghang.repository.CategoryRepository;

@Service
public class CategoryService {
    private CategoryRepository categoryRepo;
    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo= categoryRepo;
    } 
    public List<Category> findParenCategories() {
        return categoryRepo.findByParentIsNull();
    }
}
