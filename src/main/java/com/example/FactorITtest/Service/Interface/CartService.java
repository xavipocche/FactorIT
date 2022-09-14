package com.example.FactorITtest.Service.Interface;

import com.example.FactorITtest.DTO.Response.CartsResponse;
import com.example.FactorITtest.Entities.CartEntity;
import com.example.FactorITtest.Exceptions.CartException;
import java.util.Optional;

/**
 *
 * @author Xavier Pocchettino
 */
public interface CartService {
    
    CartsResponse getAllCarts();
    
    Optional<CartEntity> getCartById(Long id) throws CartException;
    
    CartEntity saveCart() throws CartException;
    
    Boolean deleteCartById(Long id) throws CartException;
}
