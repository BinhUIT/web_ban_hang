package com.example.webbanghang.service;

import java.util.ArrayList;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.Cart;
import com.example.webbanghang.model.entity.CartItem;
import com.example.webbanghang.model.entity.ProductVariant;
import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.model.redismodel.CartData;
import com.example.webbanghang.repository.ProductVariantRepository;



@Service
public class CartService {
    private final UserService userService;
    private final ProductVariantRepository productVariantRepo;
    public CartService(@Lazy UserService userService, ProductVariantRepository productVariantRepo) {
        this.userService = userService;
        this.productVariantRepo = productVariantRepo;
    }
    public Cart createCartFromMetaData(CartData data, String email) throws Exception {
        User user = (User) userService.loadUserByUsername(email);
        if(user.getId()!=data.getUserId()) {
            throw new Exception("401");
        }
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
        cart.setCartItems(new ArrayList<>());
        for(int i=0;i<data.getListDetails().size();i++) {
            ProductVariant pv= productVariantRepo.findById(data.getListDetails().get(i).getProductVariantId()).orElse(null);
            if(pv==null) {
                throw new Exception("401");
            }
            CartItem cartItem = new CartItem(0,pv,cart,data.getListDetails().get(i).getAmount());
            cart.getCartItems().add(cartItem);

            
        }
        return cart;
    }
}
