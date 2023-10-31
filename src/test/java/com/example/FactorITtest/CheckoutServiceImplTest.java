package com.example.FactorITtest;

import com.example.FactorITtest.Constants.IConstants;
import com.example.FactorITtest.Entities.CheckoutEntity;
import com.example.FactorITtest.Repository.CheckoutRepository;
import com.example.FactorITtest.Service.Impl.CheckoutServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author Xavier Pocchettino
 */
@ExtendWith(SpringExtension.class)
public class CheckoutServiceImplTest {
    
    @InjectMocks
    CheckoutServiceImpl checkoutServiceImpl;
    
    @Mock
    CheckoutRepository checkoutRepository;
    
    @Test
    @DisplayName("Test get all checkouts ok")
    void getAllCheckoutsOk() {
        List<CheckoutEntity> checkoutListExpected = new ArrayList();
        CheckoutEntity checkoutEntity = generateCheckout();
        checkoutListExpected.add(checkoutEntity);
        
        Mockito.when(checkoutRepository.findAll()).thenReturn(checkoutListExpected);
        
        List<CheckoutEntity> checkoutListActual = checkoutServiceImpl.getAllCheckouts();
        
        assertNotNull(checkoutListActual);
        assertEquals(checkoutEntity.getCustomerFullName(), checkoutEntity.getCustomerFullName());
        assertEquals(checkoutEntity.getCustomerId(), checkoutEntity.getCustomerId());
        assertEquals(checkoutEntity.getDetail(), checkoutEntity.getDetail());
        assertEquals(checkoutEntity.getCustomerId(), checkoutEntity.getCustomerId());
        assertEquals(checkoutEntity.getStatus(), checkoutEntity.getStatus());
        assertEquals(checkoutEntity.getSaleDate(), checkoutEntity.getSaleDate());
    }
    
    private CheckoutEntity generateCheckout() {
        CheckoutEntity checkoutEntity = 
                CheckoutEntity.builder()
                    .customerFullName("cliente nuevo")
                    .detail("details")
                    .customerId(1L)
                    .status(IConstants.SUCCESS_SALE)
                    .saleDate(LocalDateTime.now())
                    .build();
        return checkoutEntity;
    }
}
