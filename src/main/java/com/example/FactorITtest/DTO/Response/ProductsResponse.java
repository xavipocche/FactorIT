package com.example.FactorITtest.DTO.Response;

import com.example.FactorITtest.Entities.ProductEntity;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Xavier Pocchettino
 */
@Data
@Builder
public class ProductsResponse {
    List<ProductEntity> listProducts;
    Long totalProducts;
}
