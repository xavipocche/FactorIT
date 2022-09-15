package com.example.FactorITtest.Service.Interface;

import com.example.FactorITtest.DTO.Response.CartStatusResponse;
import com.example.FactorITtest.DTO.Response.CartsResponse;
import com.example.FactorITtest.Entities.CartEntity;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.UserException;
import java.util.Optional;

/**
 *
 * @author Xavier Pocchettino
 */
public interface CartService {
    
    CartsResponse getAllCarts();
    
    Optional<CartEntity> getCartById(Long id) throws CartException;
    
    CartEntity saveCart(Long userId) throws CartException, UserException;
    
    Boolean deleteCartById(Long id) throws CartException;
    
    CartStatusResponse getCartStatus(Long id) throws CartException;
    
    CartEntity addProduct(Long cartId, Long productId) throws CartException;
    
    CartEntity deleteProduct(Long cartId, Long productId) throws CartException;
}
