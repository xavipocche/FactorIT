package com.example.FactorITtest.Controller;

import com.example.FactorITtest.DTO.Request.CartRequest;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.Utils.WebUtils;
import com.example.FactorITtest.Service.Interface.CartService;
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
    
    @PostMapping("/save")
    public ResponseEntity saveCart(@RequestBody CartRequest cartRequest) {
        try {
            return ResponseEntity.ok(cartService.saveCart(cartRequest));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-01", e);
        }
    }
    
    @GetMapping("/getAll")
    public ResponseEntity getAllCarts() {
        try {
            return ResponseEntity.ok(cartService.getAllCarts());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-02", e);
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(cartService.getCartById(id).get());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-03", e);
        }
    }
    
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
    
    @PostMapping("/addProduct/{id}")
    public ResponseEntity addProduct(@PathVariable("id") Long cartId,
            @RequestParam Long productId) {
        try {
            return ResponseEntity.ok(cartService.addProduct(cartId, productId));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-06", e);
        }
    }
    
    @PostMapping("/deleteProduct/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long cartId,
            @RequestParam Long productId) {
        try {
            return ResponseEntity.ok(cartService.deleteProduct(cartId, productId));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-07", e);
        }
    }
    
    @GetMapping("/getStatusCart/{id}")
    public ResponseEntity getStatusCart(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(cartService.getCartStatus(id));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-08", e);
        }
    }
    
    @PostMapping("/pay/{id}")
    public ResponseEntity payCart(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(cartService.payCart(id));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-09", e);
        }
    }
}
