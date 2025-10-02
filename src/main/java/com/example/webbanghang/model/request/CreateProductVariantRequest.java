package com.example.webbanghang.model.request;

public class CreateProductVariantRequest {
    private String name;
    private int colorId;
    private int sizeId;
    private float price;
    private int quantity;
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public CreateProductVariantRequest() {

    }  
    public CreateProductVariantRequest(String name, int colorId, int sizeId, float price, int quantity) {
        this.name=name;
        this.colorId= colorId;
        this.sizeId = sizeId;
        this.price= price;
        this.quantity = quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity= quantity;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    

}
