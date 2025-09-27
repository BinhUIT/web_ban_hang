package com.example.webbanghang.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.webbanghang.middleware.ImageExtention;
import com.example.webbanghang.model.entity.Category;
import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductColor;
import com.example.webbanghang.model.entity.ProductSize;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.request.CreateProductRequest;
import com.example.webbanghang.model.request.CreateProductVariantRequest;
import com.example.webbanghang.model.request.UpdateProductRequest;
import com.example.webbanghang.model.request.UpdateProductVariantRequest;
import com.example.webbanghang.repository.CategoryRepository;
import com.example.webbanghang.repository.OrderItemRepository;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.repository.PaymentRepository;
import com.example.webbanghang.repository.ProductColorRepository;
import com.example.webbanghang.repository.ProductRepository;
import com.example.webbanghang.repository.ProductSizeRepository;
import com.example.webbanghang.repository.ProductVariantRepository;
import com.example.webbanghang.repository.UserRepository;

import io.jsonwebtoken.io.IOException;

@Service
public class AdminService {
    private final OrderRepository orderRepo;
    private final PaymentRepository paymentRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final ProductService productService;
    private final ProductColorRepository productColorRepo; 
    private final ProductSizeRepository productSizeRepo;
    private final ProductVariantRepository productVariantRepo;
    private final OrderItemRepository orderItemRepo;
    
    public AdminService(OrderRepository orderRepo, PaymentRepository paymentRepo, UserRepository userRepo, CategoryRepository categoryRepo, ProductRepository productRepo,
    ProductService productService, ProductColorRepository productColorRepo, ProductSizeRepository productSizeRepo, ProductVariantRepository productVariantRepo, OrderItemRepository orderItemRepo
    ) {
        this.orderRepo = orderRepo;
        this.paymentRepo= paymentRepo;
        this.userRepo = userRepo;
        this.categoryRepo= categoryRepo;
        this.productRepo = productRepo;
        this.productService= productService;
        this.productColorRepo= productColorRepo;
        this.productSizeRepo= productSizeRepo;
        this.productVariantRepo = productVariantRepo;
        this.orderItemRepo = orderItemRepo;
        
    } 
    public Page<Order> findAllOrder(int size, int number) {
        Pageable pageable = PageRequest.of(number,size,Sort.by(Sort.Direction.DESC,"createAt"));
        return orderRepo.findAll(pageable);
    }
    public void cancelOrder(int orderId) throws Exception {
        Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(order.getStatus()!=EOrderStatus.PENDING) {
            throw new Exception("400");
        } 
        order.setStatus(EOrderStatus.CANCELED);
        order.setUpdateAt(new Date()); 
        orderRepo.save(order);
    }
    public void shippedOrder(int orderId) throws Exception {
         Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(order.getStatus()!=EOrderStatus.SHIPPING) {
            throw new Exception("400");
        }
        if(!order.getIsPaid()) {
            throw new Exception("400");
        }
        Payment payment = order.getPayment();
        payment.setStatus("SUCCESS"); 
        paymentRepo.save(payment);
        order.setStatus(EOrderStatus.RECEIVED);
        order.setUpdateAt(new Date()); 
        orderRepo.save(order);
    }
    public void shipOrder(int orderId) throws Exception {
         Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(order.getStatus()!=EOrderStatus.PENDING) {
            throw new Exception("400");
        }
        /*if(!order.getIsPaid()) {
            throw new Exception("400");
        }*/
        order.setStatus(EOrderStatus.SHIPPING);
        order.setUpdateAt(new Date()); 
        orderRepo.save(order);
    }
    public Order getOrderById(int orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }
    public Page<User> findAllUser(int size, int number) {
        Pageable pageable = PageRequest.of(number, size);
        return userRepo.findByRole_Id(2, pageable);
    }
    public Product createProduct(CreateProductRequest request, MultipartFile image) throws Exception {
        Category category = categoryRepo.findById((long)request.getCategoryId()).orElse(null);
        if(category==null||(category.getChildren()!=null&&!category.getChildren().isEmpty())) {
            throw new Exception("Category not found");
        }
        Product product = new Product(request, category);
        
        try {
            String imagePath = ImageExtention.saveImage( image,"product");
            product.setImage(imagePath);
            
        }
        catch(IOException e) {
            
            throw new Exception("Fail to upload image, please upload again");
        }
        return productRepo.save(product);
    }
    public ProductVariant addVariant(CreateProductVariantRequest request, int productId, MultipartFile image) throws Exception {
        Product product = productRepo.findById(productId).orElse(null);
        if(product==null) {
            throw new Exception("Product not found");

        }
        ProductColor productColor = productColorRepo.findById(request.getColorId()).orElse(null);
        if(productColor==null) {
            throw new Exception("Color not found");
        } 
        ProductSize productSize = productSizeRepo.findById(request.getSizeId()).orElse(null);
        if(productSize==null) {
            throw new Exception("Size not found");
        }
        if(productService.isVariantExist(request.getColorId(), request.getSizeId(), product)) {
            throw new Exception("Variant exists");
        }
        
        ProductVariant  productVariant = new ProductVariant(request,productColor, productSize);
        product.addVariant(productVariant);
        try {
             String imagePath = ImageExtention.saveImage( image,"product_variant");
             productVariant.setImage(imagePath);
        }
        catch(IOException e) {
            
            throw new Exception("Fail to upload image, please upload again");
        }
        productRepo.save(product);
        return productVariantRepo.save(productVariant);

    }
    public Product updateProduct(UpdateProductRequest request, int productId, MultipartFile file) throws Exception {
         Product product = productRepo.findById(productId).orElse(null);
        if(product==null) {
            throw new Exception("Product not found");

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
                
                throw new Exception("Fail to upload image, please upload again");
            }
        }
        productRepo.save(product);
        return product;
    }
    public ProductVariant updateProductVariant(UpdateProductVariantRequest request, int productVariantId, MultipartFile image ) throws Exception {
        ProductVariant productVariant = productVariantRepo.findById(productVariantId).orElse(null);
        if(productVariant==null) {
            throw new Exception("Product variant not found");
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
            
            throw new Exception("Fail to upload image, please upload again");
        }
        
        
        

    }
    productRepo.save(product);
    return productVariantRepo.save(productVariant);
}
    public void deleteProduct(int productId) throws Exception {
        Product product = productRepo.findById(productId).orElse(null);
        if(product==null) {
            throw new Exception("Product not found");

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
                throw new Exception("Can not delete");
            }
        }
        
        orderItemRepo.deleteAll(listOrderItem);
        productVariantRepo.deleteAll(product.getProductVariants());
        productRepo.delete(product);
    }
    public void deleteProductVariant(int productVariantId) throws Exception {
        ProductVariant productVariant = productVariantRepo.findById(productVariantId).orElse(null);
        if(productVariant==null) {
            throw new Exception("Product variant not found");
        }
        List<OrderItem> listOrderItem = orderItemRepo.findByProductVariant_Id(productVariantId);
        for(OrderItem item:listOrderItem) {
            if(item.getOrder().getStatus()==EOrderStatus.PENDING||item.getOrder().getStatus()==EOrderStatus.SHIPPING) {
                throw new Exception("Can not delete");
            }
        }
        
        Product product = productVariant.getProduct();
        product.updateVarian();
        productRepo.save(product);
        orderItemRepo.deleteAll(listOrderItem);
        productVariantRepo.delete(productVariant);
    }

}
