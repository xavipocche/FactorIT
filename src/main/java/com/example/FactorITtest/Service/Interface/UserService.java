package com.example.FactorITtest.Service.Interface;

import com.example.FactorITtest.Entities.UserEntity;
import com.example.FactorITtest.Exceptions.UserException;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author Xavier Pocchettino
 */
public interface UserService {
    
    List<UserEntity> getAllUsers();
    
    Optional<UserEntity> getUserById(Long id) throws UserException;
    
    UserEntity saveUser(UserEntity user);
    
    Boolean deleteUserById(Long id) throws UserException;
    
}
