package com.example.webbanghang.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webbanghang.middleware.Constants;
import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.entity.CartItem;
import com.example.webbanghang.model.entity.Coupon;
import com.example.webbanghang.model.entity.Order;
import com.example.webbanghang.model.entity.OrderItem;
import com.example.webbanghang.model.entity.Payment;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.enums.EDiscountType;
import com.example.webbanghang.model.enums.EOrderStatus;
import com.example.webbanghang.model.enums.EPaymentType;
import com.example.webbanghang.model.request.AddToCartRequest;
import com.example.webbanghang.model.request.LoginRequest;
import com.example.webbanghang.model.request.OrderSubInfo;
import com.example.webbanghang.model.request.UpdateCartRequest;
import com.example.webbanghang.model.response.CheckCouponResponse;
import com.example.webbanghang.model.response.CreateOrderResponse;
import com.example.webbanghang.model.response.LoginResponse;
import com.example.webbanghang.repository.CartItemRepository;
import com.example.webbanghang.repository.CartRepository;
import com.example.webbanghang.repository.CouponRepository;
import com.example.webbanghang.repository.OrderItemRepository;
import com.example.webbanghang.repository.OrderRepository;
import com.example.webbanghang.repository.PaymentRepository;
import com.example.webbanghang.repository.ProductVariantRepository;
import com.example.webbanghang.repository.UserRepository;
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CartRepository cartRepo;
    private final ProductVariantRepository productVariantRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final CouponRepository couponRepo;
    private final PaymentRepository paymentRepo;
    private final CouponService couponService;
    private final PaymentService paymentService;
    public UserService(UserRepository userRepo,  JwtService jwtService, @Lazy AuthenticationManager authenticationManager, CartRepository cartRepo, ProductVariantRepository productVariantRepo, CartItemRepository cartItemRepo,
    OrderRepository orderRepo, OrderItemRepository orderItemRepo, CouponRepository couponRepo, PaymentRepository paymentRepo,@Lazy PaymentService paymentService, @Lazy CouponService couponService) {
        this.userRepo= userRepo;
        this.cartRepo= cartRepo;
        this.jwtService= jwtService;
        this.authenticationManager= authenticationManager;
        this.productVariantRepo= productVariantRepo;
        this.cartItemRepo = cartItemRepo;
        this.orderRepo= orderRepo;
        this.orderItemRepo= orderItemRepo;
        this.couponRepo = couponRepo;
        this.paymentRepo = paymentRepo;
        this.paymentService = paymentService;
        this.couponService= couponService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user==null) {
            throw new UsernameNotFoundException("User not found");
        } 
        return user;
    }
    public LoginResponse login(LoginRequest request) {
       
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if(auth.isAuthenticated()) {
            Object userPrincipal = auth.getPrincipal();
            if(userPrincipal instanceof User user) {
                return new LoginResponse(user, jwtService.generateToken(user.getEmail()), new Date(System.currentTimeMillis()+Constants.tokenExpireTime*60*1000));
            }
            return null;
        }
        return null;
    }
    public CartItem addToCart(AddToCartRequest request, String email) throws Exception {
        User user = (User) this.loadUserByUsername(email);
        Cart cart= user.getCart();
        if(cart==null) {
            Cart newCart = new Cart();
            newCart.setUser(user); 
            newCart.setCreateAt(new Date()); 
            newCart.setAmount(0);
            cart=newCart;
            cartRepo.save(newCart);
        }
        else {
            for(CartItem cItem: cart.getCartItems()) {
                if(cItem.getProductVariant().getId()==request.getProductVariantId()) {
                    throw new Exception("400");
                }
            }
        }
        cart.setAmount(cart.getAmount()+1);
        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart); 
        ProductVariant productVariant = productVariantRepo.findById(request.getProductVariantId()).orElse(null);
        if(productVariant==null) {
            throw new Exception("404");
        }
        newCartItem.setProductVariant(productVariant); 
        newCartItem.setAmount(request.getAmount());

        cartRepo.save(cart);
        cartItemRepo.save(newCartItem);
        return newCartItem;

    }
    private CartItem validateUpdateRequest(UpdateCartRequest request, User user) throws Exception {
        CartItem cartItem = cartItemRepo.findById(request.getCartItemId()).orElse(null);
        if(cartItem==null) {
            throw new Exception("404");
        }
        if(cartItem.getCart().getId()!=user.getCart().getId()) {
            throw new Exception("401");
        }
        return cartItem;
    }
    public CartItem updateCart(UpdateCartRequest request, String email) throws Exception {
        User user = (User) this.loadUserByUsername(email);
        if(user.getCart()==null) {
            throw new Exception("401");
        }
        CartItem cartItem = validateUpdateRequest(request, user);
        cartItem.setAmount(request.getNewAmount());
        cartItemRepo.save(cartItem);
        return cartItem;
    } 
    public void deleteCartItem(int cartItemId, String email) throws Exception {
        User user = (User) this.loadUserByUsername(email);
        if(user.getCart()==null) {
            throw new Exception("401");
        }
        CartItem cartItem = cartItemRepo.findById(cartItemId).orElse(null);
        if(cartItem==null) {
            throw new Exception("404");
        }
        if(cartItem.getCart().getId()!=user.getCart().getId()) {
            throw new Exception("401");
        }
        Cart cart = user.getCart();
        cart.setAmount(cart.getAmount()-1);
        cartRepo.save(cart);
        cartItemRepo.delete(cartItem); 
    }
    public void clearCart(String email) throws Exception {
        User user = (User) this.loadUserByUsername(email);
        if(user.getCart()==null) {
            throw new Exception("401");
        }
        List<CartItem> listCartItem = user.getCart().getCartItems();
        Cart cart = user.getCart();
        cart.setAmount(0);
        cartRepo.save(cart);
        cartItemRepo.deleteAll(listCartItem);
    }
    public Cart getUserCart(String email) {
        User user = (User) this.loadUserByUsername(email);
        return user.getCart();
    }
    public List<CartItem> updateAllCartItem(List<UpdateCartRequest> requests, String email) throws Exception {
        User user = (User) this.loadUserByUsername(email);
        if(user.getCart()==null) {
            throw new Exception("401");
        }
        List<CartItem> listCartItem = new ArrayList<>();
        for(UpdateCartRequest request:requests) {
            CartItem cartItem = validateUpdateRequest(request, user);
            cartItem.setAmount(request.getNewAmount());
            listCartItem.add(cartItem);
        }
        cartItemRepo.saveAll(listCartItem);
        return listCartItem;
    }
    public CreateOrderResponse orderFromCart(String email, OrderSubInfo subInfo) throws Exception {
        User user = (User) this.loadUserByUsername(email);
        if(user.getCart()==null) {
            throw new Exception("401");
        }
        Order order = new Order(user, subInfo.getAddress(), subInfo.getPhone()); 
        
        float productMoney=0;
        float shippingFee=0;
        List<OrderItem> listOrderItem = new ArrayList<>();
        for(CartItem cartItem :user.getCart().getCartItems()) {
            productMoney= productMoney+cartItem.getAmount()*cartItem.getProductVariant().getPrice();
            shippingFee=shippingFee+Constants.shippingFee;
           listOrderItem.add(new OrderItem(cartItem, order));
           ProductVariant productVariant = cartItem.getProductVariant();
           productVariant.setCanDelete(false);
           productVariantRepo.save(productVariant);
           cartItemRepo.delete(cartItem);
        }
        
        order.setShipping_fee(shippingFee);
        order.setOriginPrice(productMoney+shippingFee); 
        if(subInfo.getCouponCode()!=null&&!subInfo.getCouponCode().equals("")) {
            CheckCouponResponse checkCoupon = couponService.checkCoupon(subInfo.getCouponCode(), productMoney);
            Coupon coupon = checkCoupon.getCoupon();
            if(coupon==null) {
                throw new Exception("400");
            }
            order.setCoupon(coupon);
            if(coupon.getDiscountType()==EDiscountType.FIXED) {
                order.setTotal(order.getOriginPrice()-coupon.getDiscount());
            }
            else {
                order.setTotal(order.getOriginPrice()*(1-coupon.getDiscount()));
            }

        }
        else {
            order.setTotal(productMoney+shippingFee);
        }
        
        orderRepo.save(order);
        orderItemRepo.saveAll(listOrderItem);
        if(subInfo.getPaymentType().equals(EPaymentType.COD)) {
            Payment payment = new Payment(order,"VND","UNPAID",EPaymentType.COD);
            paymentRepo.save(payment);
            return new CreateOrderResponse(order.getId(), "Success");
        }
        else {
            return new CreateOrderResponse(order.getId(), paymentService.createPaymentLink(order));
        }
        
        
    }
    public Page<Order> getUserOrderHistory(String email, Pageable pageable) {
        User user =(User) this.loadUserByUsername(email);
        return orderRepo.findByUser_IdOrderByCreateAtDesc(user.getId(), pageable);
    }
    public Order getOrderById(String email, int id) throws Exception {
        User user = (User) this.loadUserByUsername(email);
        Order order = orderRepo.findById(id).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(user.getId()!=order.getUser().getId()) {
            throw new Exception("400");
        } 
        return order;
    }
    public Order cancelOrder(String email, int id) throws Exception {
         User user = (User) this.loadUserByUsername(email);
        Order order = orderRepo.findById(id).orElse(null);
        if(order==null) {
            throw new Exception("404");
        } 
        if(user.getId()!=order.getUser().getId()||order.getStatus()!=EOrderStatus.PENDING) {
            throw new Exception("400");
        } 
        order.setStatus(EOrderStatus.CANCELED);
        order.setUpdateAt(new Date()); 
        
        orderRepo.save(order);
        return order;
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
        User user = (User) this.loadUserByUsername(email);
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
        return order;
    }
}
