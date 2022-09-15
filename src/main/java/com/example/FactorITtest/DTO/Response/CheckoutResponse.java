package com.example.FactorITtest.DTO.Response;

import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Entities.UserEntity;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Xavier Pocchettino
 */
@Data
@Builder
public class CheckoutResponse {
    UserEntity user;
    List<ProductEntity> detail;
    BigDecimal total;
    String status;
    String discount;
}
