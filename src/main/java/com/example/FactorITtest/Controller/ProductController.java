package com.example.FactorITtest.Controller;

import com.example.FactorITtest.DTO.Request.ProductRequest;
import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Exceptions.Utils.WebUtils;
import com.example.FactorITtest.Service.Interface.ProductService;
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
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductService productService;
    
    @PostMapping("/save")
    public ResponseEntity saveProduct(@RequestBody ProductRequest productRequest) {
        try {
            return ResponseEntity.ok(productService.saveProduct(productRequest));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-PRODUCT-01", e);
        }
    }    
    
    @GetMapping("/getAll")
    public ResponseEntity getAllProducts() {
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-PRODUCT-02", e);
        }        
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id).get());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-PRODUCT-03", e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProductById(@PathVariable("id") Long id) {
        try {
            if(productService.deleteProductById(id)) {
               return ResponseEntity.ok("El producto se eliminó correctamente");
            } else {
               return WebUtils.generateResponseEntityFromException("ERROR-PRODUCT-04", 
                       new ProductException("No se pudo eliminar el producto")); 
            }
        } catch (Exception e) {
          return WebUtils.generateResponseEntityFromException("ERROR-PRODUCT-05", e);
        }
    }    
}
