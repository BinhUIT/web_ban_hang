package com.example.webbanghang.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="CATEGORIES")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long id;
    private String name;
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name="parent_id", nullable=true) 
    private Category parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL) 
    private List<Category> children;
    @OneToMany(mappedBy = "category") 
    private List<Product> products;
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    @JsonIgnore
    public Category getParent() {
        return parent;
    }
    public void setParent(Category parent) {
        this.parent = parent;
    }
    public List<Category> getChildren() {
        return children;
    }
    public void setChildren(List<Category> children) {
        this.children = children;
    }
    public Category() {
    }

    public Category(long id, String name, String description, String image, Category parent, List<Category> children,
            List<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.parent = parent;
        this.children = children;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    

    
}
