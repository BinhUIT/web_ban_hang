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
        return categoryRepo.findByParentIdOrName(null, "");
    }
    public List<Category> findByParentId(long parentId) {
        return categoryRepo.findByParentIdOrName(parentId,"");
    }
    public Category findById(long id) {
        return categoryRepo.findById(id).orElse(null);
    }
    public List<Category> findLeafCategories(Category category) {
        List<Category> res= new ArrayList<>();
        dfs(category,res);
        return res;
    }
    public void dfs(Category category, List<Category> res) { 
        if(category.getChildren()==null||category.getChildren().isEmpty()) {
                return;
            }
        for(Category cat: category.getChildren()) {
            if(cat.getChildren()==null||cat.getChildren().isEmpty()) {
                res.add(cat);
            }
            else {
                dfs(cat,res);
            }
        }
    }
    public List<Category> findByName(String name) {
        return categoryRepo.findByParentIdOrName(-1L, name);
    }
    public List<Category> findAll(){
        return categoryRepo.findAll();
    }
   
}
