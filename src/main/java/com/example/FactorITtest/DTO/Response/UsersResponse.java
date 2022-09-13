package com.example.FactorITtest.DTO.Response;

import com.example.FactorITtest.Entities.UserEntity;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Xavier Pocchettino
 */
@Builder
@Getter
@Setter
public class UsersResponse {
    List<UserEntity> listUsers;
    Long totalUsers;
}
