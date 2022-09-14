package com.example.FactorITtest.DTO.Response;

import com.example.FactorITtest.Entities.UserEntity;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Xavier Pocchettino
 */
@Data
@Builder
public class AddBalanceResponse {
    UserEntity user;
    String message;
}
