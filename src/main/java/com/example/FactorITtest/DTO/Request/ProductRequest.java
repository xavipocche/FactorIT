package com.example.FactorITtest.DTO.Request;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Xavier Pocchettino
 */
@Data
@Builder
public class ProductRequest {
    String name;
    String description;
    BigDecimal price;
}
