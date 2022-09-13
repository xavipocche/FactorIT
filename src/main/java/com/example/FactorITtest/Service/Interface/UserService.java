package com.example.FactorITtest.Service.Interface;

import com.example.FactorITtest.DTO.Request.UserRequest;
import com.example.FactorITtest.DTO.Response.UsersResponse;
import com.example.FactorITtest.Entities.UserEntity;
import com.example.FactorITtest.Exceptions.UserException;
import java.math.BigDecimal;
import java.util.Optional;


/**
 *
 * @author Xavier Pocchettino
 */
public interface UserService {
    
    UsersResponse getAllUsers();
    
    Optional<UserEntity> getUserById(Long id) throws UserException;
    
    UserEntity saveUser(UserRequest userRequest) throws UserException;
    
    Boolean deleteUserById(Long id) throws UserException;
    
    Boolean addBalance(Long idUser, BigDecimal balance) throws UserException; 
}
