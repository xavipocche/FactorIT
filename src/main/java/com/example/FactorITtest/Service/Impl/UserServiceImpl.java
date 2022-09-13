package com.example.FactorITtest.Service.Impl;

import com.example.FactorITtest.Entities.UserEntity;
import com.example.FactorITtest.Exceptions.UserException;
import com.example.FactorITtest.Repository.UserRepository;
import com.example.FactorITtest.Service.Interface.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xavier Pocchettino
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(Long id) throws UserException {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        
        if(userOptional.isPresent()) {
            return userOptional;
        } else {
            throw new UserException("No se encontró el usuario solicitado");
        }
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        UserEntity userEntity = 
                UserEntity.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .build();
                
        return userRepository.save(userEntity);
    }

    @Override
    public Boolean deleteUserById(Long id) throws UserException {
        Optional<UserEntity> userOptional = getUserById(id);
            
        if(userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        } else {
            throw new UserException("No se encontró el usuario solicitado para su eliminación");
        }
    }
}
