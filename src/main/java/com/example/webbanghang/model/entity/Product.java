package com.example.webbanghang.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.webbanghang.middleware.UUIDGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    @ManyToOne
    @JoinColumn(name="category_id") 
    private Category category; 
    private String name;
    private String shortDesc;
    private String detailDesc;
    private Date create_at;
    private Date update_at;
    private float rating;
    private int minPrice;
    private int maxPrice;
    private long quantity;
    private long sold;
    private boolean isEnable;
    private String image;
    @OneToMany(mappedBy = "product") 
    private List<ProductVariant> productVariants;
    @JsonIgnore
    @OneToMany(mappedBy="product")
    private List<TagProducts> tagProducts;
    @Column(nullable = false, unique = true, updatable = false)
    private String code;
    @PrePersist
    public void generateCode() {
        if(this.code ==null) {
            this.code = UUIDGenerator.getRanDomUUID();

        }
    }
    
    @JsonIgnore
    public Category getCategory() {
        return category;
    }
    @JsonIgnore
    public List<TagProducts> getTagProducts() {
        return this.tagProducts;
    } 
    public void setTagProducts(List<TagProducts> tagProducts) {
        this.tagProducts= tagProducts;
    } 
    public List<String> getTags() {
        return this.tagProducts.stream().map(item->item.getTag().getName()).collect(Collectors.toList());
    }
    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }
    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShortDesc() {
        return shortDesc;
    }
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }
    public String getDetailDesc() {
        return detailDesc;
    }
    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }
    public Date getCreate_at() {
        return create_at;
    }
    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
    public Date getUpdate_at() {
        return update_at;
    }
    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }
    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public float getMinPrice() {
        return minPrice;
    }
   
    
    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }
    public float getMaxPrice() {
        return maxPrice;
    }
    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
    public long getQuantity() {
        return quantity;
    }
    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
    public long getSold() {
        return sold;
    }
    public void setSold(long sold) {
        this.sold = sold;
    }
    public boolean isEnable() {
        return isEnable;
    }
    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    } 
    public void setCategory(Category category) {
        this.category= category;
    } 
    public List<String> getCategories() {
        List<String> res = new ArrayList<>();
        Category currentCategory = this.category;
        while(currentCategory!=null) {
            res.add(currentCategory.getName());
            currentCategory=currentCategory.getParent();
        }
        return res;
    }
    public Product() {
    }
    public Product(int id, Category category, String name, String shortDesc, String detailDesc, Date create_at,
            Date update_at, float rating, int minPrice, int maxPrice, long quantity, long sold, boolean isEnable,
            String image, List<ProductVariant> productVariants, List<TagProducts> tagProducts, String code) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.shortDesc = shortDesc;
        this.detailDesc = detailDesc;
        this.create_at = create_at;
        this.update_at = update_at;
        this.rating = rating;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.quantity = quantity;
        this.sold = sold;
        this.isEnable = isEnable;
        this.image = image;
        this.productVariants = productVariants;
        this.tagProducts= tagProducts;
        this.code = code;
    }
    
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
   
    
    
    
    
}
