package com.example.FactorITtest.DTO.Request;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Xavier Pocchettino
 */
@Data
@Builder
public class UserRequest {
    String name;
    String lastname;
    String email;
    BigDecimal balance;
}
