package com.example.FactorITtest.Service.Interface;

import com.example.FactorITtest.DTO.Request.CartRequest;
import com.example.FactorITtest.DTO.Response.CartStatusResponse;
import com.example.FactorITtest.DTO.Response.CartsResponse;
import com.example.FactorITtest.DTO.Response.CheckoutResponse;
import com.example.FactorITtest.Entities.CartEntity;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Exceptions.UserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;

/**
 *
 * @author Xavier Pocchettino
 */
public interface CartService {
    
    CartsResponse getAllCarts();
    
    Optional<CartEntity> getCartById(Long id) throws CartException;
    
    CartEntity saveCart(CartRequest cartRequest) throws CartException, UserException;
    
    Boolean deleteCartById(Long id) throws CartException;
    
    CartStatusResponse getCartStatus(Long id) throws CartException;
    
    CartEntity addProduct(Long cartId, Long productId) throws CartException, ProductException;
    
    CartEntity deleteProduct(Long cartId, Long productId) throws CartException, ProductException;
    
    CheckoutResponse payCart(Long cartId) throws CartException, UserException, JsonProcessingException;
}
