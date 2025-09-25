package com.example.webbanghang.model.request;

public class CreateProductRequest {
    private int categoryId;
    String name;
    String shortDesc;
    String detailDesc;
    public CreateProductRequest() {

    } 
    public CreateProductRequest(int categoryId, String name, String shortDesc, String detailDesc) {
        this.categoryId = categoryId;
        this.name= name;
        this.shortDesc = shortDesc;
        this.detailDesc = detailDesc;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
    
}
