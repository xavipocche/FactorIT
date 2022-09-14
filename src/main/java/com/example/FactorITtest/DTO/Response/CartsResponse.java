package com.example.FactorITtest.DTO.Response;

import com.example.FactorITtest.Entities.CartEntity;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Xavier Pocchettino
 */
@Data
@Builder
public class CartsResponse {
    List<CartEntity> listCarts;
    Long totalCarts;
}
