package com.example.FactorITtest.Controller;

import com.example.FactorITtest.DTO.Request.CartRequest;
import com.example.FactorITtest.DTO.Response.CartStatusResponse;
import com.example.FactorITtest.DTO.Response.CartsResponse;
import com.example.FactorITtest.DTO.Response.CheckoutResponse;
import com.example.FactorITtest.Entities.CartEntity;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.Utils.WebUtils;
import com.example.FactorITtest.Service.Interface.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    CartService cartService;
    
    @Operation(summary = "Saves a cart")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Cart saved successfully", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CartEntity.class)) }),
        @ApiResponse(responseCode = "422", description = "User not found", 
            content = @Content)
    })     
    @PostMapping("/save")
    public ResponseEntity saveCart(@RequestBody CartRequest cartRequest) {
        try {
            return ResponseEntity.ok(cartService.saveCart(cartRequest));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-01", e);
        }
    }

    @Operation(summary = "Gets all the carts")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Get all carts success", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CartsResponse.class)) }),
    })         
    @GetMapping("/getAll")
    public ResponseEntity getAllCarts() {
        try {
            return ResponseEntity.ok(cartService.getAllCarts());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-02", e);
        }
    }
    
    @Operation(summary = "Gets cart by ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Get cart by ID success", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CartEntity.class)) }),
        @ApiResponse(responseCode = "422", description = "Cart not found", 
            content = @Content)
    })     
    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(cartService.getCartById(id).get());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-03", e);
        }
    }
    
    @Operation(summary = "Deletes cart by ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Cart deleted succesfully", 
            content = { @Content(mediaType = "application/json") }),
        @ApiResponse(responseCode = "422", description = "Cart not found", 
            content = @Content)
    })      
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCartById(@PathVariable("id") Long id) {
        try {
            if(cartService.deleteCartById(id)) {
                return ResponseEntity.ok("El carrito se elimió correctamente");
            } else {
                return WebUtils.generateResponseEntityFromException("ERROR-CART-04",
                        new CartException("No se pudo eliminar el carrito"));
            }
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-05", e);
        }
    }

    @Operation(summary = "Adds product to cart")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Product added succesfuly", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CartEntity.class)) }),
        @ApiResponse(responseCode = "422", description = "Cart not found, The product is already assigned to another cart", 
            content = @Content)
    })     
    @PostMapping("/addProduct/{id}")
    public ResponseEntity addProduct(@PathVariable("id") Long cartId,
            @RequestParam Long productId) {
        try {
            return ResponseEntity.ok(cartService.addProduct(cartId, productId));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-06", e);
        }
    }
    
    @Operation(summary = "Deletes product from cart")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Product deleted from cart succesfuly", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CartEntity.class)) }),
        @ApiResponse(responseCode = "422", description = "Cart not found, You cannot delete a product that is assigned in a cart", 
            content = @Content)
    })      
    @PostMapping("/deleteProduct/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long cartId,
            @RequestParam Long productId) {
        try {
            return ResponseEntity.ok(cartService.deleteProduct(cartId, productId));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-07", e);
        }
    }
    
    @Operation(summary = "Shows info about the cart, list of products and total to pay")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Shows info successfully", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CartStatusResponse.class)) }),
        @ApiResponse(responseCode = "422", description = "Cart not found", 
            content = @Content)
    })      
    @GetMapping("/getStatusCart/{id}")
    public ResponseEntity getStatusCart(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(cartService.getCartStatus(id));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-08", e);
        }
    }
    
    @Operation(summary = "Pay the cart, comparing the user´s balance with the total to pay from the cart")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Cart paid successfully and register the sale y the table sales", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CheckoutResponse.class)) }),
        @ApiResponse(responseCode = "422", description = "Cart not found, User doesn´t have enough balance to pay the cart", 
            content = @Content)
    })     
    @PostMapping("/pay/{id}")
    public ResponseEntity payCart(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(cartService.payCart(id));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-09", e);
        }
    }
}
