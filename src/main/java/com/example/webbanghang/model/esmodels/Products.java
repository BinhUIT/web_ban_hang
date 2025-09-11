package com.example.webbanghang.model.esmodels;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.example.webbanghang.model.entity.Product;

@Document(indexName="products_essearch", createIndex=false)
public class Products {
    @Id
    private String id;
    private String name;
    private String shortDesc;
    private String detailDesc;
    public Products() {

    }
    public Products(String id, String name, String shortDesc, String detailDesc) {
        this.id= id;
        this.name= name;
        this.shortDesc= shortDesc;
        this.detailDesc= detailDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public Products(Product p) {
        this.id=Integer.toString(p.getId());
        this.name= p.getName();
        this.shortDesc= p.getShortDesc();
        this.detailDesc = p.getDetailDesc();
    }
}
