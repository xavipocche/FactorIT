package com.example.FactorITtest.DTO.Response;

import com.example.FactorITtest.Entities.CartEntity;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Xavier Pocchettino
 */
@Data
@Builder
public class CartStatusResponse {
    CartEntity cartEntity;
    Integer totalProducts;
    BigDecimal totalToPay;
}
