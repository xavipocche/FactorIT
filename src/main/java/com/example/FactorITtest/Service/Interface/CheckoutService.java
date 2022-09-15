package com.example.FactorITtest.Service.Interface;

import com.example.FactorITtest.Entities.CheckoutEntity;
import java.util.List;

/**
 *
 * @author Xavier Pocchettino
 */
public interface CheckoutService {
    
    List<CheckoutEntity> getAllCheckouts();
    
    CheckoutEntity saveCheckout(CheckoutEntity checkoutEntity);
}
