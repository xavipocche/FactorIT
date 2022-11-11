package com.example.FactorITtest.Controller;

import com.example.FactorITtest.DTO.Request.UserRequest;
import com.example.FactorITtest.DTO.Response.AddBalanceResponse;
import com.example.FactorITtest.DTO.Response.UsersResponse;
import com.example.FactorITtest.Entities.UserEntity;
import com.example.FactorITtest.Exceptions.UserException;
import com.example.FactorITtest.Exceptions.Utils.WebUtils;
import com.example.FactorITtest.Service.Interface.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @Operation(summary = "Saves a user")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Save success", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = UserEntity.class)) }),
        @ApiResponse(responseCode = "422", description = "Some of the request params were null or empty", 
            content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        try {
            return ResponseEntity.ok(userService.saveUser(userRequest));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-USER-01", e);
        }
    }
    
    @Operation(summary = "Gets all users")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Get all users success", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = UsersResponse.class)) })
    })    
    @GetMapping("/getAll")
    public ResponseEntity getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-USER-02", e);
        }
    }

    @Operation(summary = "Gets user by ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Get user by ID success", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = UserEntity.class)) }),
        @ApiResponse(responseCode = "422", description = "User not found", 
            content = @Content)
    })    
    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id).get());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-USER-03", e);
        }
    }
    
    @Operation(summary = "Deletes a user by ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "User deleted succesfully", 
            content = { @Content(mediaType = "application/json") }),
        @ApiResponse(responseCode = "422", description = "User not found", 
            content = @Content)
    })      
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUserById(@PathVariable("id") Long id) {
        try {
            if(userService.deleteUserById(id)) {
               return ResponseEntity.ok("El usuario se eliminó correctamente");
            } else {
               return WebUtils.generateResponseEntityFromException("ERROR-USER-04", 
                       new UserException("No se pudo eliminar el usuario")); 
            }
        } catch (Exception e) {
          return WebUtils.generateResponseEntityFromException("ERROR-USER-05", e);
        }
    }
    
    @Operation(summary = "Adds balance to a user")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Balance added successfully", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = AddBalanceResponse.class)) }),
        @ApiResponse(responseCode = "422", description = "User not found", 
            content = @Content)
    })       
    @PostMapping("/addBalance/{id}")
    public ResponseEntity addBalance(@PathVariable("id") Long userId, 
            @RequestParam BigDecimal balance) {
        try {
             return ResponseEntity.ok(userService.addBalance(userId, balance));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-USER-06", e);
        }    
    }
    

    @Operation(summary = "Gets all vip users")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "A vip users are users who were spent 10000 or more in the current month", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = UserEntity.class)) })
    })       
    @GetMapping("/getVipUsers")
    public ResponseEntity getVipUsers() {
        try {
            return ResponseEntity.ok(userService.getVipUsers());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-USER-07", e);
        }
    }
}
