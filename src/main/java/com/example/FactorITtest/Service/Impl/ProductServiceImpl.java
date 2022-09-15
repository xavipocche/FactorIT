package com.example.FactorITtest.Service.Impl;

import com.example.FactorITtest.DTO.Request.ProductRequest;
import com.example.FactorITtest.DTO.Response.ProductsResponse;
import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Repository.ProductRepository;
import com.example.FactorITtest.Service.Interface.ProductService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xavier Pocchettino
 */
@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductsResponse getAllProducts() {
        List<ProductEntity> listProducts = (List) productRepository.findAll();
        
        ProductsResponse productsResponse = 
                ProductsResponse.builder()
                    .listProducts(listProducts)
                    .totalProducts(productRepository.countProducts())
                    .build();
        
        return productsResponse;        
    }

    @Override
    public Optional<ProductEntity> getProductById(Long id) throws ProductException {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        
        if(productOptional.isPresent()) {
            return productOptional;
        } else {
            throw new ProductException("No se encontró el producto solicitado");
        }        
    }

    @Override
    public ProductEntity saveProduct(ProductRequest productRequest) throws ProductException {
        validateData(productRequest);
        
        if(productRequest.getPrice() == null) {
            productRequest.setPrice(BigDecimal.ZERO);
        }
        
        ProductEntity productEntity = 
                ProductEntity.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .build();
        
        return productRepository.save(productEntity);
    }

    @Override
    public Boolean deleteProductById(Long id) throws ProductException {
        Optional<ProductEntity> productOptional = getProductById(id);
            
        if(productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
            return true;
        } else {
            throw new ProductException("No se encontró el producto solicitado para su eliminación");
        }        
    }
    
    private void validateData(ProductRequest productRequest) throws ProductException {
        if(productRequest.getName().trim().isEmpty()) {
            throw new ProductException("El nombre del producto no puede ser nulo");
        }
        if(productRequest.getDescription().trim().isEmpty()) {
            throw new ProductException("La descripción del producto no puede ser nula");
        }
    }    
}
