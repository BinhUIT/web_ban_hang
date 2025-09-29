package com.example.webbanghang.service.adminservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.webbanghang.exception.BadRequestException;
import com.example.webbanghang.exception.InternalServerErrorException;
import com.example.webbanghang.exception.NotFoundException;
import com.example.webbanghang.middleware.ImageExtention;
import com.example.webbanghang.model.entity.Category;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.request.CreateProductRequest;
import com.example.webbanghang.model.request.UpdateProductRequest;
import com.example.webbanghang.repository.CategoryRepository;
import com.example.webbanghang.repository.OrderItemRepository;
import com.example.webbanghang.repository.ProductRepository;
import com.example.webbanghang.repository.ProductVariantRepository;

import io.jsonwebtoken.io.IOException;
@Service
public class AdminProductService {
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final ProductVariantRepository productVariantRepo;
    private final OrderItemRepository orderItemRepo;
    public AdminProductService(CategoryRepository categoryRepo, ProductRepository productRepo, ProductVariantRepository productVariantRepo, OrderItemRepository orderItemRepo) {
        this.categoryRepo= categoryRepo;
        this.productRepo = productRepo;
        this.productVariantRepo= productVariantRepo;
        this.orderItemRepo= orderItemRepo;
    }
     public Product createProduct(CreateProductRequest request, MultipartFile image) throws Exception {
        Category category = categoryRepo.findById((long)request.getCategoryId()).orElse(null);
        if(category==null||(category.getChildren()!=null&&!category.getChildren().isEmpty())) {
            throw new BadRequestException("Category not found");
        }
        Product product = new Product(request, category);
        
        try {
            String imagePath = ImageExtention.saveImage( image,"product");
            product.setImage(imagePath);
            
        }
        catch(IOException e) {
            
            throw new InternalServerErrorException("Fail to upload image, please upload again");
        }
        return productRepo.save(product);
    }
    public Product updateProduct(UpdateProductRequest request, int productId, MultipartFile file) throws Exception {
         Product product = productRepo.findById(productId).orElse(null);
        if(product==null) {
            throw new NotFoundException("Product not found");

        }
        if(request!=null) {
            product.updateInfo(request);
        }
        
        if(file!=null) {
                try {
                String imagePath = ImageExtention.saveImage( file,"product");
                product.setImage(imagePath);
                
                
            }
            catch(IOException e) {
                
                throw new InternalServerErrorException("Fail to upload image, please upload again");
            }
        }
        productRepo.save(product);
        return product;
    }
    public void deleteProduct(int productId) throws Exception {
        Product product = productRepo.findById(productId).orElse(null);
        if(product==null) {
            throw new NotFoundException("Product not found");

        }
        if(product.getProductVariants()==null||product.getProductVariants().isEmpty()) {
            productRepo.delete(product);
            return ;
        }
        List<Integer> listVariantId = product.getProductVariants().stream().map((item)->{
            return item.getId();
        }).collect(Collectors.toList());
        List<OrderItem> listOrderItem = orderItemRepo.findByProductVariant_IdIn(listVariantId);
        for(OrderItem item:listOrderItem) {
            if(item.getOrder().getStatus()==EOrderStatus.PENDING||item.getOrder().getStatus()==EOrderStatus.SHIPPING) {
                throw new BadRequestException("Can not delete");
            }
        }
        
        orderItemRepo.deleteAll(listOrderItem);
        productVariantRepo.deleteAll(product.getProductVariants());
        productRepo.delete(product);
    }
}
