package com.example.FactorITtest.Service.Impl;

import com.example.FactorITtest.Entities.CheckoutEntity;
import com.example.FactorITtest.Repository.CheckoutRepository;
import com.example.FactorITtest.Service.Interface.CheckoutService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xavier Pocchettino
 */
@Service
public class CheckoutServiceImpl implements CheckoutService {
    
    @Autowired
    CheckoutRepository checkoutRepository;

    @Override
    public List<CheckoutEntity> getAllCheckouts() {
        return (List<CheckoutEntity>) checkoutRepository.findAll();
    }

    @Override
    public CheckoutEntity saveCheckout(CheckoutEntity checkoutEntity) {
        return checkoutRepository.save(checkoutEntity);
    }
}
