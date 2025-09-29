package com.example.webbanghang.service.productservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Category;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.repository.CategoryRepository;
import com.example.webbanghang.repository.ProductRepository;
import com.example.webbanghang.repository.ProductVariantRepository;
import com.example.webbanghang.service.CategoryService;

@Service
public class ProductQueryService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final CategoryService categoryService;
    private final ProductVariantRepository productVariantRepo;
    private final ProductVariantService productVariantService;

    public ProductQueryService(ProductRepository productRepo,
                               CategoryRepository categoryRepo,
                               CategoryService categoryService,
                               ProductVariantRepository productVariantRepo,
                               ProductVariantService productVariantService) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.categoryService = categoryService;
        this.productVariantRepo = productVariantRepo;
        this.productVariantService = productVariantService;
    }
    public Page<Product> findAllEnableProductByCategory(Pageable pageable, long catId) {
        Category category = categoryRepo.findById(catId).orElse(null);
        if (category == null) return Page.empty();
        List<Integer> res = new ArrayList<>();
        if (category.getChildren() == null || category.getChildren().isEmpty()) {
            for (Product p : category.getProducts()) {
                res.add(p.getId());
            }
        } else {
            List<Category> listLeaf = categoryService.findLeafCategories(category);
            for (Category leafCat : listLeaf) {
                for (Product p : leafCat.getProducts()) {
                    res.add(p.getId());
                }
            }
        }
        return productRepo.findByIsEnableAndIdIn(true, res, pageable);
    }

    public Page<Product> findByPriceBetween(int from, int to, Pageable pageable) {
        return productRepo.findByIsEnableAndMinPriceBetween(true, from, to, pageable);
    }

    public Page<Product> findBySizeAndColor(List<Integer> sizeIds, List<Integer> colorIds, Pageable pageable) {
        List<ProductVariant> findBySize = new ArrayList<>();
        List<ProductVariant> findByColor = new ArrayList<>();

        if (sizeIds != null && !sizeIds.isEmpty()) {
            findBySize = productVariantRepo.findByProductSize_IdIn(sizeIds);
        }
        if (colorIds != null && !colorIds.isEmpty()) {
            findByColor = productVariantRepo.findByProductColor_IdIn(colorIds);
        }

        Set<Integer> setId = productVariantService.extractProductIdFromVariants(findBySize);
        setId.addAll(productVariantService.extractProductIdFromVariants(findByColor));

        List<Integer> listId = new ArrayList<>(setId);
        return productRepo.findByIsEnableAndIdIn(true, listId, pageable);
    }

    public Page<Product> findByListCategory(List<Long> listCategories, Pageable pageable) {
        return productRepo.findByIsEnableAndCategory_IdIn(true, listCategories, pageable);
    }
}
