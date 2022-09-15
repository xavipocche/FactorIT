package com.example.FactorITtest.DTO.Request;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Xavier Pocchettino
 */
@Data
@Builder
public class CartRequest {
    Long userId;
    String type;
}
