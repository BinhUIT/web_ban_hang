package com.example.webbanghang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Category;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.esmodels.Products;
import com.example.webbanghang.repository.CategoryRepository;
import com.example.webbanghang.repository.ProductRepository;
import com.example.webbanghang.repository.ProductVariantRepository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;


@Service
public class ProductService {
    private ProductRepository productRepo;
    private CategoryRepository categoryRepo;
    private CategoryService categoryService;
    private ProductVariantRepository productVariantRepo;
    private ElasticsearchClient elasticsearchClient;
    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo, CategoryService categoryService, ProductVariantRepository productVariantRepo,
    ElasticsearchClient elasticsearchClient) {
        this.productRepo= productRepo;
        this.categoryRepo= categoryRepo;
        this.categoryService = categoryService;
        this.productVariantRepo = productVariantRepo;
        this.elasticsearchClient= elasticsearchClient;
    } 
    public Product getById(int id) {
        return productRepo.findById(id).orElse(null);
    }
    
    
    public Page<Product> findAllEnableProductsByPage(Pageable pageable) { 
        return productRepo.findByIsEnable(true, pageable);

    }

    public Page<Product> findAllEnableProductByCategory(Pageable pageable,long catId ) {
        Category category = categoryRepo.findById(catId).orElse(null);
        if(category==null) return null;
        List<Integer> res= new ArrayList();
        if(category.getChildren()==null||category.getChildren().isEmpty()) {
            for(Product cat:category.getProducts()) {
                res.add(cat.getId());
            }
        }
        else {
            List<Category> listLeaf = categoryService.findLeafCategories(category);
            for(Category leafCat: listLeaf) {
                for(Product cat:leafCat.getProducts()) {
                res.add(cat.getId());
            }
            } 
        }

        return productRepo.findByIsEnableAndIdIn(true,res, pageable);
    }
    public Page<Product> findByPriceBetween(int from, int to, Pageable pageable) {
        return productRepo.findByIsEnableAndMinPriceBetween(true, from, to, pageable);
    }
    public Set<Integer> extractProductIdFromVariants(List<ProductVariant> variants) {
        Set<Integer> idSet = new HashSet<>();
        for(ProductVariant pv: variants) {
            idSet.add(pv.getProduct().getId());
        } 
        return idSet;
    }
    public Page<Product> findBySizeAndColor(List<Integer> sizeIds, List<Integer> colorIds,Pageable pageable) {
        List<ProductVariant> findBySize= new ArrayList<>();
        List<ProductVariant> findByColor = new ArrayList<>();
        if(sizeIds!=null) {
            findBySize = productVariantRepo.findByProductSize_IdIn(sizeIds); 
        }
        if(colorIds!=null) {
            findByColor = productVariantRepo.findByProductColor_IdIn(colorIds); 
        }
        Set<Integer> setId = extractProductIdFromVariants(findBySize);
        setId.addAll(extractProductIdFromVariants(findByColor));
        List<Integer> listId = new ArrayList<>(setId);
        return productRepo.findByIsEnableAndIdIn(true, listId, pageable);
    }
    public Page<Product> findByListCategory(List<Long> listCategories, Pageable pageable) {
        return productRepo.findByIsEnableAndCategory_IdIn(true, listCategories, pageable);
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
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }
    public Page<Product> findByListIds(List<Integer> listIds, Pageable page) {
        return productRepo.findByIdIn(listIds, page);
    }
    public boolean isVariantExist(int colorId, int sizeId, Product product) {
        for(ProductVariant v: product.getProductVariants()) {
            if(v.getProductColor().getId()==colorId) {
                if(v.getProductSize().getId()==sizeId) {
                    return true;
                }
            }
        }
        return false;
    }
    public ProductVariant getVariantById(int variantId) {
        return productVariantRepo.findById(variantId).orElse(null);
    }
}
