package com.example.FactorITtest;

import com.example.FactorITtest.Constants.IConstants;
import com.example.FactorITtest.DTO.Response.AddBalanceResponse;
import com.example.FactorITtest.DTO.Response.UsersResponse;
import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Entities.UserEntity;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Exceptions.UserException;
import com.example.FactorITtest.Repository.UserRepository;
import com.example.FactorITtest.Service.Impl.UserServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Xavier Pocchettino
 */
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
    
    @InjectMocks
    UserServiceImpl userServiceImpl;
    
    @Mock
    UserRepository userRepository;
    
    @Test
    @DisplayName("Get all users ok")
    void getUsersOk() {
        List<UserEntity> listUser = generateListUsers();
        
        UsersResponse usersResponseExpected = 
                UsersResponse.builder()
                    .listUsers(listUser)
                    .totalUsers(Long.valueOf(listUser.size()))
                    .build();
        
        Mockito.when(userRepository.findAll()).thenReturn(listUser);
        
        UsersResponse usersResponseActual = userServiceImpl.getAllUsers();
        
        assertNotNull(usersResponseActual);
        assertEquals(usersResponseExpected.getListUsers(), usersResponseActual.getListUsers());
        assertEquals(usersResponseExpected.getListUsers().get(0).getName(), usersResponseActual.getListUsers().get(0).getName());
        assertEquals(usersResponseExpected.getListUsers().get(0).getLastname(), usersResponseActual.getListUsers().get(0).getLastname());
        assertEquals(usersResponseExpected.getListUsers().get(0).getEmail(), usersResponseActual.getListUsers().get(0).getEmail());
    }
    
    @Test
    @DisplayName("Get user by id ok")
    void getUserByIdOk() throws UserException {
        UserEntity userExpected = CartServiceImplTest.generateUser();
        Optional<UserEntity> userOptional = Optional.of(userExpected);
        
        Mockito.when(userRepository.findById(userExpected.getId())).thenReturn(userOptional);
        
        UserEntity userActual = userServiceImpl.getUserById(userExpected.getId()).get();
        
        assertNotNull(userActual);
        assertEquals(userExpected.getName(), userActual.getName());
        assertEquals(userExpected.getLastname(), userExpected.getLastname());
        assertEquals(userActual.getEmail(), userActual.getEmail());
        assertEquals(userExpected.getBalance(), userActual.getBalance());
        
    }
    
    @Test
    @DisplayName("Get user by id not found")
    void getUserByIdNotFound() {
        UserEntity userExpected = CartServiceImplTest.generateUser();
        
        Mockito.when(userRepository.findById(userExpected.getId())).thenReturn(Optional.empty());
        
        Throwable t = assertThrows(UserException.class, () -> 
                userServiceImpl.getUserById(userExpected.getId()));
        assertEquals("No se encontró el usuario solicitado", t.getMessage());        
    }
    
    @Test
    @DisplayName("Delete user by id ok")
    void deleteUserOk() throws UserException {
        Boolean expected = Boolean.TRUE;
        UserEntity userExpected = CartServiceImplTest.generateUser();
        Optional<UserEntity> userOptional = Optional.of(userExpected);
        
        Mockito.when(userRepository.findById(userExpected.getId())).thenReturn(userOptional);
        
        Boolean actual = userServiceImpl.deleteUserById(userExpected.getId());
        
        assertNotNull(expected);
        assertEquals(expected, actual);        
    }
    
    @Test
    @DisplayName("Delete user not found")
    void deleteUserNotFound() throws CartException, ProductException {
        UserEntity userExpected = CartServiceImplTest.generateUser();
        
        Mockito.when(userRepository.findById(userExpected.getId())).thenReturn(Optional.empty());

        Throwable t = assertThrows(UserException.class, () -> 
                userServiceImpl.deleteUserById(userExpected.getId()));
        assertEquals("No se encontró el usuario solicitado", t.getMessage());        
    }
    
    @Test
    @DisplayName("Add balance ok")
    void addBalanceOk() throws UserException {
        BigDecimal amountToAdd = new BigDecimal("1000");
        UserEntity userExpected = CartServiceImplTest.generateUser();
        Optional<UserEntity> userOptional = Optional.of(userExpected);
        
        AddBalanceResponse addBalanceResponseExpected =
                AddBalanceResponse.builder()
                    .user(userExpected)
                    .message(IConstants.SUCCESS_MESSAGE_ADD_BALANCE)
                    .build();
        
        Mockito.when(userRepository.findById(userExpected.getId())).thenReturn(userOptional);

        AddBalanceResponse addBalanceResponseActual = userServiceImpl.addBalance(userExpected.getId(), amountToAdd);
        
        assertNotNull(addBalanceResponseActual);
        assertEquals(addBalanceResponseExpected.getUser().getBalance(), addBalanceResponseActual.getUser().getBalance());
        assertEquals(addBalanceResponseExpected.getUser(), addBalanceResponseActual.getUser());
        assertEquals(addBalanceResponseExpected.getMessage(), addBalanceResponseActual.getMessage());
    }     
    
    private List<UserEntity> generateListUsers() {
        List<UserEntity> listUsers = new ArrayList();
        listUsers.add(CartServiceImplTest.generateUser());
        
        return listUsers;
    }

}
