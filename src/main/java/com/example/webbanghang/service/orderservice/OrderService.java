package com.example.webbanghang.service.orderservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webbanghang.exception.BadRequestException;
import com.example.webbanghang.exception.NotFoundException;
import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.entity.CartItem;
import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.enums.EPaymentType;
import com.example.webbanghang.model.request.OrderSubInfo;
import com.example.webbanghang.model.response.CreateOrderResponse;
import com.example.webbanghang.repository.OrderItemRepository;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.repository.PaymentRepository;
import com.example.webbanghang.repository.ProductVariantRepository;
import com.example.webbanghang.service.UserService;
import com.example.webbanghang.service.couponservice.CouponService;
import com.example.webbanghang.service.paymentservice.PaymentService;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    private final UserService userService;
    private final ProductVariantRepository productVariantRepo;
    private final CouponService couponService;

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepo;
    private final InventoryService inventoryService;
    public OrderService(UserService userService, ProductVariantRepository productVariantRepo, CouponService couponService, OrderRepository orderRepo, OrderItemRepository orderItemRepo, PaymentService paymentService, PaymentRepository paymentRepo, InventoryService inventoryService) {
        this.userService= userService;
        this.productVariantRepo= productVariantRepo;
        this.couponService= couponService;
        this.orderItemRepo= orderItemRepo;
        this.orderRepo= orderRepo;
        this.paymentRepo = paymentRepo;
        this.paymentService= paymentService;
        this.inventoryService= inventoryService;
    }
    @Transactional
    public CreateOrderResponse orderFromCart(String email, OrderSubInfo subInfo, Cart cart) throws Exception {
        User user = (User) userService.loadUserByUsername(email);
        Order order = new Order(user, subInfo.getAddress(), subInfo.getPhone()); 
        float productMoney=addOrderItems(order, cart);
        order.setShipping_fee(Constants.shippingFee);
        order.setOriginPrice(productMoney+Constants.shippingFee); 
        applyCouponIfNeed(order, subInfo, productMoney);
        orderRepo.save(order);
        orderItemRepo.saveAll(order.getOrderItems());
        return handlePayment(order, subInfo.getPaymentType());        
        
    }
    private float addOrderItems(Order order, Cart cart) {
        float productMoney=0;
        List<OrderItem> listOrderItem = new ArrayList<>();
        List<ProductVariant> listVariants= new ArrayList<>();
        for(CartItem cartItem :cart.getCartItems()) {
            productMoney= productMoney+cartItem.getAmount()*cartItem.getProductVariant().getPrice();
            
           listOrderItem.add(new OrderItem(cartItem, order));
           ProductVariant productVariant = cartItem.getProductVariant();
           productVariant.setCanDelete(false);
          listVariants.add(productVariant);
           
        }
        order.setOrderItems(listOrderItem);
        productVariantRepo.saveAll(listVariants);
        return productMoney;
    }
    private void applyCouponIfNeed(Order order, OrderSubInfo subInfo, float productMoney) throws Exception {
        if(subInfo.getCouponCode()!=null&&!subInfo.getCouponCode().equals("")) {
            Coupon coupon = couponService.validateCoupon(subInfo.getCouponCode(), productMoney);
            order.setCoupon(coupon);
            couponService.increaseUsage(coupon);
            order.setTotal(couponService.applyDiscount(productMoney, coupon));

        }
        else {
            order.setTotal(productMoney+Constants.shippingFee);
        }
    }
    private CreateOrderResponse handlePayment(Order order, EPaymentType paymentType) throws Exception {
        if (paymentType.equals(EPaymentType.COD)) {
            Payment payment = new Payment(order, "VND", "UNPAID", EPaymentType.COD);
            paymentRepo.save(payment);
            return new CreateOrderResponse(order.getId(), "Success");
        }
        return new CreateOrderResponse(order.getId(), paymentService.createPaymentLink(order));
    }
    public Page<Order> getUserOrderHistory(String email, Pageable pageable) {
            User user =(User) userService.loadUserByUsername(email);
            return orderRepo.findByUser_IdOrderByCreateAtDesc(user.getId(), pageable);
    }
    public Order getOrderById(String email, int id) throws Exception {
        User user = (User) userService.loadUserByUsername(email);
        Order order = orderRepo.findById(id).orElse(null);
        if(order==null) {
            throw new NotFoundException("Order with id="+id+" does not exist");
        } 
        if(user.getId()!=order.getUser().getId()) {
            throw new BadRequestException("You can not access this order");
        } 
        return order;
    }
    @Transactional
    public Order cancelOrder(String email, int id) throws Exception {
        User user = (User) userService.loadUserByUsername(email);
        Order order = orderRepo.findById(id).orElse(null);
        if(order==null) {
            throw new NotFoundException("Order with id="+id+" does not exist");
        } 
        if(user.getId()!=order.getUser().getId()||order.getStatus()!=EOrderStatus.PENDING) {
            throw new BadRequestException("You can not cancel this order");
        } 
        if(order.getStatus()==EOrderStatus.SHIPPING) {
           inventoryService.restoreInventory(order.getOrderItems());
        }
        order.setStatus(EOrderStatus.CANCELED);
        order.setUpdateAt(new Date()); 
        
        orderRepo.save(order);
        
        return order;
    }
        
        
    
   
    
    @Transactional
    public Order receivedOrder(int orderId, String email) throws Exception {
        User user = (User) userService.loadUserByUsername(email);
        Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new NotFoundException("Order with id "+orderId+" does not exist");
        } 
        if(user.getId()!=order.getUser().getId()||order.getStatus()!=EOrderStatus.SHIPPING) {
            throw new BadRequestException("You can not cancel this order");
        } 
        Payment payment = order.getPayment();
        payment.setStatus("Success"); 
        paymentRepo.save(payment);
        order.setStatus(EOrderStatus.RECEIVED);
        orderRepo.save(order);
        
        inventoryService.increaseSold(order.getOrderItems());
        return order;
    }
    

}
