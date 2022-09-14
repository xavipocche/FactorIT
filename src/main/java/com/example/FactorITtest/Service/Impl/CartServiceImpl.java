package com.example.FactorITtest.Service.Impl;

import com.example.FactorITtest.DTO.Response.CartsResponse;
import com.example.FactorITtest.Entities.CartEntity;
import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.UserException;
import com.example.FactorITtest.Repository.CartRepository;
import com.example.FactorITtest.Service.Interface.CartService;
import com.example.FactorITtest.Service.Interface.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xavier Pocchettino
 */
@Service
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;

    @Override
    public CartsResponse getAllCarts() {
        List<CartEntity> listCarts = (List) cartRepository.findAll();
        
        CartsResponse cartsResponse =
                CartsResponse.builder()
                    .listCarts(listCarts)
                    .totalCarts(cartRepository.countCarts())
                    .build();
        
        return cartsResponse;
    }

    @Override
    public Optional<CartEntity> getCartById(Long id) throws CartException {
        Optional<CartEntity> cartOptional = cartRepository.findById(id);
        
        if(cartOptional.isPresent()) {
            return cartOptional;
        } else {
            throw new CartException("No se encontró el carrito solicitado");
        }
    }

    @Override
    public CartEntity saveCart(Long userId) throws CartException, UserException {
        List<ProductEntity> productList = new ArrayList();
        
        CartEntity cartEntity = 
                CartEntity.builder()
                .user(userService.getUserById(userId).get())
                .product(productList)
                .build();
        
        return cartRepository.save(cartEntity);
    }

    @Override
    public Boolean deleteCartById(Long id) throws CartException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
