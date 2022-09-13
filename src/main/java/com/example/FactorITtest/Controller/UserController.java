package com.example.FactorITtest.Controller;

import com.example.FactorITtest.DTO.Request.UserRequest;
import com.example.FactorITtest.Exceptions.UserException;
import com.example.FactorITtest.Exceptions.Utils.WebUtils;
import com.example.FactorITtest.Service.Interface.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Xavier Pocchettino
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) throws UserException{
        try {
            return ResponseEntity.ok(userService.saveUser(userRequest));
        } catch (Exception e) {
            return (WebUtils.generateResponseEntityFromException("ERROR-01", e));
        }
    }
    
    @GetMapping("/getAll")
    public ResponseEntity getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return (WebUtils.generateResponseEntityFromException("ERROR-02", e));
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id).get());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-03", e);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id") Long id) {
        try {
            if(userService.deleteUserById(id)) {
               return ResponseEntity.ok("El usuario se eliminó correctamente");
            } else {
               return WebUtils.generateResponseEntityFromException("ERROR-04", 
                       new UserException("No se pudo eliminar el usuario")); 
            }
        } catch (Exception e) {
          return WebUtils.generateResponseEntityFromException("ERROR-05", e);
        }
    }
}
