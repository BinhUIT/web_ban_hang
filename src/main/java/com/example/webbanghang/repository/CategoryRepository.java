package com.example.webbanghang.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    public List<Category> findByParentIdOrName(Long parentId, String name);
    public Category findFirstByName(String name);
}
