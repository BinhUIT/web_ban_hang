package com.example.webbanghang.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.esmodels.Products;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;



@Service
public class ESService {
    private final ElasticsearchClient elasticsearchClient;

    public ESService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public boolean saveAllCurrentProduct(List<Product> products) {
        List<Products> listProducts = products.stream()
                .map(Products::new)   // convert SQL entity → ES model
                .collect(Collectors.toList());

        for (Products p : listProducts) {
            try {
                IndexResponse response = elasticsearchClient.index(i -> i
                        .index("products_essearch")
                        .id(p.getId())   // dùng id SQL làm _id trong ES
                        .document(p)
                );
                System.out.println("Indexed product id=" + p.getId() + " result=" + response.result());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public Products findById(int id) {
        try {
            return elasticsearchClient.get(g -> g
                            .index("products_es")
                            .id(Integer.toString(id)),
                    Products.class
            ).source();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
