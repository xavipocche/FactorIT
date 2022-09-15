package com.example.FactorITtest.Service.Interface;

import com.example.FactorITtest.DTO.Request.ProductRequest;
import com.example.FactorITtest.DTO.Response.ProductsResponse;
import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Exceptions.ProductException;
import java.util.Optional;

/**
 *
 * @author Xavier Pocchettino
 */
public interface ProductService {
    
    ProductsResponse getAllProducts();
    
    Optional<ProductEntity> getProductById(Long id) throws ProductException;
    
    ProductEntity saveProduct(ProductRequest productRequest) throws ProductException;
    
    Boolean deleteProductById(Long id) throws ProductException;
}
