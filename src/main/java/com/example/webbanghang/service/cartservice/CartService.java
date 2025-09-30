package com.example.webbanghang.service.cartservice;

import java.util.ArrayList;

import org.springframework.stereotype.Service;


import com.example.webbanghang.exception.NotFoundException;
import com.example.webbanghang.exception.UnauthorizedException;
import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.entity.CartItem;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.redismodel.CartData;
import com.example.webbanghang.repository.ProductVariantRepository;
import com.example.webbanghang.repository.UserRepository;
import com.example.webbanghang.service.UserService;



@Service
public class CartService {
    private final UserRepository userRepo;
    private final ProductVariantRepository productVariantRepo;
    private final UserService userService;
    public CartService(UserRepository userRepo, ProductVariantRepository productVariantRepo, UserService userService) {
        this.userRepo = userRepo;
        this.productVariantRepo = productVariantRepo;
        this.userService = userService;
    }
    public Cart getCartFromMetaData(CartData cartData) throws RuntimeException{
        User user = this.userRepo.findById(cartData.getUserId()).orElse(null);
        if(user==null) {
            throw new NotFoundException("User not found");
        }
        Cart cart = user.getCart();
        if(cart==null) {
            cart = new Cart();
        }
        cart.setCartItems(new ArrayList<>());
        for(int i=0;i<cartData.getListDetails().size();i++) {
            int amountInCart=0;
            ProductVariant pVariant = productVariantRepo.findById(cartData.getListDetails().get(i).getProductVariantId()).orElse(null);
            if(pVariant!=null) {
                CartItem cartItem= new CartItem();
                cartItem.setProductVariant(pVariant);
                cartItem.setAmount(cartData.getListDetails().get(i).getAmount());
                cartItem.setId(i);
                cart.getCartItems().add(cartItem);
                amountInCart++;
            }
            cart.setAmount(amountInCart);
        }
        return cart;
    }
    public Cart createCartFromMetaData(CartData data, String email) throws RuntimeException {
        User user = (User) userService.loadUserByUsername(email);
        if(user.getId()!=data.getUserId()) {
            throw new UnauthorizedException("Unauthorized");
        }
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        cart.setCartItems(new ArrayList<>());
        for(int i=0;i<data.getListDetails().size();i++) {
            ProductVariant pv= productVariantRepo.findById(data.getListDetails().get(i).getProductVariantId()).orElse(null);
            if(pv==null) {
                throw new NotFoundException("Variant "+data.getListDetails().get(i).getProductVariantId()+" not found");
            }
            CartItem cartItem = new CartItem(0,pv,cart,data.getListDetails().get(i).getAmount());
            cart.getCartItems().add(cartItem);

            
        }
        return cart;
    }
}
