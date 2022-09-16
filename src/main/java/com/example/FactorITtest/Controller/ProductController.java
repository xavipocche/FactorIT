package com.example.FactorITtest.Controller;

import com.example.FactorITtest.DTO.Request.ProductRequest;
import com.example.FactorITtest.DTO.Response.ProductsResponse;
import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Exceptions.Utils.WebUtils;
import com.example.FactorITtest.Service.Interface.ProductService;
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
    
    @Operation(summary = "Saves a product")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Product saved successfully", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductEntity.class)) }),
        @ApiResponse(responseCode = "422", description = "Some of the request params were null or empty", 
            content = @Content)
    })         
    @PostMapping("/save")
    public ResponseEntity saveProduct(@RequestBody ProductRequest productRequest) {
        try {
            return ResponseEntity.ok(productService.saveProduct(productRequest));
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-PRODUCT-01", e);
        }
    }    
    
    @Operation(summary = "Gets all products")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Get all products success", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductsResponse.class)) }),
    })     
    @GetMapping("/getAll")
    public ResponseEntity getAllProducts() {
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-PRODUCT-02", e);
        }        
    }
    
    @Operation(summary = "Gets product by ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Get product by ID success", 
            content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ProductEntity.class)) }),
        @ApiResponse(responseCode = "422", description = "Product not found", 
            content = @Content)
    })      
    @GetMapping("/find/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id).get());
        } catch (Exception e) {
            return WebUtils.generateResponseEntityFromException("ERROR-PRODUCT-03", e);
        }
    }

    @Operation(summary = "Deletes product by ID")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Product deleted successfully", 
            content = { @Content(mediaType = "application/json") }),
        @ApiResponse(responseCode = "422", description = "Product not found", 
            content = @Content)
    })     
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
