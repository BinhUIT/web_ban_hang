package com.example.webbanghang.service.productservice;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanghang.model.esmodels.Products;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Service
public class ProductSearchService {
    private final ElasticsearchClient elasticsearchClient;
    public ProductSearchService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    } 
    public List<Products> searchByKeyword(String keyword) throws IOException {
       Query matchFuzzy = MatchQuery.of(m -> m
        .field("name")
        .query(keyword)
        .fuzziness("2")   
)._toQuery();
        SearchResponse<Products> response = elasticsearchClient.search(s -> s
                        .index("products_essearch") 
                        .query(matchFuzzy),
                Products.class
        );
        return response.hits().hits().stream()
                .map(Hit::source)
                .toList();


    }
}
