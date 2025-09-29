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
import com.example.webbanghang.model.entity.Product;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EDiscountType;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.enums.EPaymentType;
import com.example.webbanghang.model.request.OrderSubInfo;
import com.example.webbanghang.model.response.CheckCouponResponse;
import com.example.webbanghang.model.response.CreateOrderResponse;
import com.example.webbanghang.repository.CouponRepository;
import com.example.webbanghang.repository.OrderItemRepository;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.repository.PaymentRepository;
import com.example.webbanghang.repository.ProductRepository;
import com.example.webbanghang.repository.ProductVariantRepository;
import com.example.webbanghang.service.CouponService;
import com.example.webbanghang.service.PaymentService;
import com.example.webbanghang.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    private final UserService userService;
    private final ProductVariantRepository productVariantRepo;
    private final CouponService couponService;
    private final CouponRepository couponRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepo;
    private final ProductRepository productRepo;
    public OrderService(UserService userService, ProductVariantRepository productVariantRepo, CouponService couponService, CouponRepository couponRepo, OrderRepository orderRepo, OrderItemRepository orderItemRepo, PaymentService paymentService, PaymentRepository paymentRepo, ProductRepository productRepo) {
        this.userService= userService;
        this.productVariantRepo= productVariantRepo;
        this.couponService= couponService;
        this.couponRepo= couponRepo;
        this.orderItemRepo= orderItemRepo;
        this.orderRepo= orderRepo;
        this.paymentRepo = paymentRepo;
        this.paymentService= paymentService;
        this.productRepo= productRepo;
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
        for(CartItem cartItem :cart.getCartItems()) {
            productMoney= productMoney+cartItem.getAmount()*cartItem.getProductVariant().getPrice();
            
           listOrderItem.add(new OrderItem(cartItem, order));
           ProductVariant productVariant = cartItem.getProductVariant();
           productVariant.setCanDelete(false);
           productVariantRepo.save(productVariant);
           
        }
        order.setOrderItems(listOrderItem);
        return productMoney;
    }
    private void applyCouponIfNeed(Order order, OrderSubInfo subInfo, float productMoney) throws Exception {
        if(subInfo.getCouponCode()!=null&&!subInfo.getCouponCode().equals("")) {
            CheckCouponResponse checkCoupon = couponService.checkCoupon(subInfo.getCouponCode(), productMoney);
            Coupon coupon = checkCoupon.getCoupon();
            if(coupon==null) {
                throw new Exception("400");
            }
            order.setCoupon(coupon);
            coupon.setUsedTime(coupon.getUsedTime()+1);
            couponRepo.save(coupon);
            if(coupon.getDiscountType()==EDiscountType.FIXED) {
                order.setTotal(order.getOriginPrice()-coupon.getDiscount());
            }
            else {
                order.setTotal(order.getOriginPrice()*(1-coupon.getDiscount()));
            }

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
    public Order cancelOrder(String email, int id) throws Exception {
        User user = (User) userService.loadUserByUsername(email);
        Order order = orderRepo.findById(id).orElse(null);
        if(order==null) {
            throw new NotFoundException("Order with id="+id+" does not exist");
        } 
        if(user.getId()!=order.getUser().getId()||order.getStatus()!=EOrderStatus.PENDING) {
            throw new BadRequestException("You can not cancel this order");
        } 
        order.setStatus(EOrderStatus.CANCELED);
        order.setUpdateAt(new Date()); 
        
        orderRepo.save(order);
        if(order.getStatus()==EOrderStatus.SHIPPING) {
           cancelShipedOrder(order);
        }
        return order;
    }
        
        
    
    private void cancelShipedOrder(Order order) {
         List<OrderItem> listOrderItems = order.getOrderItems();
            List<Product> listProducts = new ArrayList<>();
            List<ProductVariant> listProductVariants = new ArrayList<>();
        for(OrderItem i:listOrderItems) {
            ProductVariant pv = i.getProductVariant();
            pv.setQuantity(pv.getQuantity()+i.getAmount());
            Product p= pv.getProduct();
            p.setQuantity(p.getQuantity()+i.getAmount());
            listProducts.add(p);
            listProductVariants.add(pv);
        }
        productRepo.saveAll(listProducts);
        productVariantRepo.saveAll(listProductVariants);
    }
    public Order applyCoupon(int orderId, String couponCode, String email) throws Exception {
        Order order = orderRepo.findById(orderId).orElse(null);
        Coupon coupon = couponRepo.findFirstByCode(couponCode);
        if(order==null||coupon==null) {
            throw new Exception("400");
        }
        if(order.getIsPaid()||order.getStatus()!=EOrderStatus.PENDING) {
            throw new Exception("400");
        }
        if(order.getOriginPrice()<coupon.getMinOrderValue()) {
            System.out.println(order.getOriginPrice());
            throw new Exception("Need buy more");
        }
        return applyCoupon(order, coupon);
    }
    public Order applyCoupon(Order order, Coupon coupon) {
        coupon.setUsedTime(coupon.getUsedTime()+1); 
        couponRepo.save(coupon);
        order.setCoupon(coupon); 
        orderRepo.save(order);
        return order;
    }
    public Order receivedOrder(int orderId, String email) throws Exception {
        User user = (User) userService.loadUserByUsername(email);
        Order order = orderRepo.findById(orderId).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(user.getId()!=order.getUser().getId()||order.getStatus()!=EOrderStatus.SHIPPING) {
            throw new Exception("400");
        } 
        Payment payment = order.getPayment();
        payment.setStatus("Success"); 
        paymentRepo.save(payment);
        order.setStatus(EOrderStatus.RECEIVED);
        orderRepo.save(order);
        List<OrderItem> listOrderItems = order.getOrderItems();
         List<Product> listProducts = new ArrayList<>();
        List<ProductVariant> listProductVariants = new ArrayList<>();
        for(OrderItem i:listOrderItems) {
            ProductVariant pv = i.getProductVariant();
            pv.setQuantity(pv.getQuantity()-i.getAmount());
            Product p= pv.getProduct();
            p.setQuantity(p.getQuantity()-i.getAmount());
            p.setSold(p.getSold()+i.getAmount());
            listProducts.add(p);
            listProductVariants.add(pv);
        }
        productRepo.saveAll(listProducts);
        productVariantRepo.saveAll(listProductVariants);

        return order;
    }

}
