package com.example.webbanghang.service.adminservice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.webbanghang.exception.BadRequestException;
import com.example.webbanghang.exception.InternalServerErrorException;
import com.example.webbanghang.exception.NotFoundException;
import com.example.webbanghang.middleware.ImageExtention;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductColor;
import com.example.webbanghang.model.entity.ProductSize;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.request.CreateProductVariantRequest;
import com.example.webbanghang.model.request.UpdateProductVariantRequest;
import com.example.webbanghang.repository.OrderItemRepository;
import com.example.webbanghang.repository.ProductColorRepository;
import com.example.webbanghang.repository.ProductRepository;
import com.example.webbanghang.repository.ProductSizeRepository;
import com.example.webbanghang.repository.ProductVariantRepository;
import com.example.webbanghang.service.productservice.ProductVariantService;

import io.jsonwebtoken.io.IOException;
@Service
public class AdminProductVariantService {
    private final ProductRepository productRepo;
    private final ProductColorRepository productColorRepo;
    private final ProductSizeRepository productSizeRepo;
    private final OrderItemRepository orderItemRepo;
    private final ProductVariantService productVariantService;
    private final ProductVariantRepository productVariantRepo;

    public AdminProductVariantService(ProductRepository productRepo, ProductColorRepository productColorRepo,
            ProductSizeRepository productSizeRepo, OrderItemRepository orderItemRepo,
            ProductVariantService productVariantService, ProductVariantRepository productVariantRepo) {
        this.productRepo = productRepo;
        this.productColorRepo = productColorRepo;
        this.productSizeRepo = productSizeRepo;
        this.orderItemRepo= orderItemRepo;
        this.productVariantService = productVariantService;
        this.productVariantRepo = productVariantRepo;
    }

    public ProductVariant addVariant(CreateProductVariantRequest request, int productId, MultipartFile image) throws Exception {
        Product product = productRepo.findById(productId).orElse(null);
        if(product==null) {
            throw new NotFoundException("Product not found");

        }
        ProductColor productColor = productColorRepo.findById(request.getColorId()).orElse(null);
        if(productColor==null) {
            throw new BadRequestException("Color not found");
        } 
        ProductSize productSize = productSizeRepo.findById(request.getSizeId()).orElse(null);
        if(productSize==null) {
            throw new BadRequestException("Size not found");
        }
        if(productVariantService.isVariantExist(request.getColorId(), request.getSizeId(), product)) {
            throw new BadRequestException("Variant exists");
        }
        
        ProductVariant  productVariant = new ProductVariant(request,productColor, productSize);
        product.addVariant(productVariant);
        try {
             String imagePath = ImageExtention.saveImage( image,"product_variant");
             productVariant.setImage(imagePath);
        }
        catch(IOException e) {
            
            throw new InternalServerErrorException("Fail to upload image, please upload again");
        }
        productRepo.save(product);
        return productVariantRepo.save(productVariant);

    }
    public ProductVariant updateProductVariant(UpdateProductVariantRequest request, int productVariantId, MultipartFile image ) throws Exception {
        ProductVariant productVariant = productVariantRepo.findById(productVariantId).orElse(null);
        if(productVariant==null) {
            throw new NotFoundException("Product variant not found");
        }
        Product product = productVariant.getProduct();
        if(request!=null) {
            productVariant.updateInfo(request);
            product.updateVarian();
        }
        if(image!=null) {
            try {
            String imagePath = ImageExtention.saveImage( image,"product");
            productVariant.setImage(imagePath);
            
            
        }
        catch(IOException e) {
            
            throw new InternalServerErrorException("Fail to upload image, please upload again");
        }
        
        
        

    }
    productRepo.save(product);
    return productVariantRepo.save(productVariant);
}
public void deleteProductVariant(int productVariantId) throws RuntimeException {
        ProductVariant productVariant = productVariantRepo.findById(productVariantId).orElse(null);
        if(productVariant==null) {
            throw new NotFoundException("Product variant not found");
        }
        List<OrderItem> listOrderItem = orderItemRepo.findByProductVariant_Id(productVariantId);
        for(OrderItem item:listOrderItem) {
            if(item.getOrder().getStatus()==EOrderStatus.PENDING||item.getOrder().getStatus()==EOrderStatus.SHIPPING) {
                throw new BadRequestException("Can not delete");
            }
            
        }
        
        Product product = productVariant.getProduct();
        product.updateVarian();
        productRepo.save(product);
        orderItemRepo.deleteAll(listOrderItem);
        productVariantRepo.delete(productVariant);
    }
}
