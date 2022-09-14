package com.example.FactorITtest.Controller;

import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.Utils.WebUtils;
import com.example.FactorITtest.Service.Interface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity saveCart(@RequestParam Long idUser) {
        try {
            return ResponseEntity.ok(cartService.saveCart(idUser));
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
    
    @GetMapping("/getStatusCart/{id}")
    public ResponseEntity getStatusCart(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(cartService.getCartStatus(id));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-CART-06", e);
        }
    }
}
