package com.example.webbanghang.model.entity;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import com.example.webbanghang.middleware.UUIDGenerator;
import com.example.webbanghang.model.enums.EEntitySatus;
import com.example.webbanghang.model.request.CreateProductVariantRequest;
import com.example.webbanghang.model.request.UpdateProductVariantRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="PRODUCT_VARIANTS")
public class ProductVariant implements Comparable<ProductVariant> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    @ManyToOne
    @JoinColumn(name="product_id") 
    @JsonIgnore
    private Product product;
    @ManyToOne
    @JoinColumn(name="product_color_id") 
    private ProductColor productColor;
    @ManyToOne
    @JoinColumn(name="product_size_id") 
    private ProductSize productSize;
    private float price;
    private long quantity;
    private String image;
    private String name;
    private Date createAt;
    private Date updateAt;
    private EEntitySatus status;
    @Column(nullable = false, unique = true, updatable = false)
    private String code;
    @ColumnDefault("true")
    private boolean canDelete;

    @PrePersist
    public void generateCode() {
        if(this.code ==null) {
            this.code = UUIDGenerator.getRanDomUUID();
        }
    }
    public void updateInfo(UpdateProductVariantRequest request) {
        this.name= request.getName();
        this.price = request.getPrice();
        this.quantity = request.getQuantity();
    }
    public ProductVariant(CreateProductVariantRequest request, ProductColor color, ProductSize size) {
        this.productColor=color;
        this.productSize = size;
        this.price= request.getPrice();
        this.name= request.getName();
        this.createAt = new Date();
        this.status= EEntitySatus.ENABLE;
        this.quantity = request.getQuantity();
        this.canDelete=true;
    }
    public ProductVariant() {
    }

    

    public Date getCreateAt() {
        return createAt;
    }



    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }



    public Date getUpdateAt() {
        return updateAt;
    }



    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }



    public EEntitySatus getStatus() {
        return status;
    }



    public void setStatus(EEntitySatus status) {
        this.status = status;
    }
    
    public ProductVariant(int id, Product product, ProductColor productColor, ProductSize productSize, float price,
            long quantity, String image, String name, Date createAt, Date updateAt, EEntitySatus status, String code, boolean canDelete) {
        this.id = id;
        this.product = product;
        this.productColor = productColor;
        this.productSize = productSize;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.name = name;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
        this.code = code;
        this.canDelete = canDelete;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonIgnore
    public Product getProduct() {
        return product;
    }
    public int getProductId() {
        return this.product.getId();
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductColor getProductColor() {
        return productColor;
    }

    public void setProductColor(ProductColor productColor) {
        this.productColor = productColor;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    
    public float getRealPrice() {
        return 0f;
    }
    @Override
    public int compareTo(ProductVariant o) {
        return Float.compare(this.id, o.price);
    }
    public boolean isCanDelete() {
        return canDelete;
    }
    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
   
    
}
