package com.example.FactorITtest;

import com.example.FactorITtest.DTO.Response.ProductsResponse;
import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Repository.ProductRepository;
import com.example.FactorITtest.Service.Impl.ProductServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author Xavier Pocchettino
 */
@ExtendWith(SpringExtension.class)
public class ProductServiceImplTest {
    
    @InjectMocks
    ProductServiceImpl productServiceImpl;
    
    @Mock
    ProductRepository productRepository;
    
    @Test
    @DisplayName("Test get all products")
    void getAllProductsOk() {
        ProductsResponse productResponseExpected = generateProductsResponse();
        
        Mockito.when(productRepository.findAll()).thenReturn(productResponseExpected.getListProducts());
        
        ProductsResponse productResponseActual = productServiceImpl.getAllProducts();
        
        assertNotNull(productResponseActual);
        assertEquals(productResponseExpected.getListProducts().get(0).getDescription(), productResponseActual.getListProducts().get(0).getDescription());
        assertEquals(productResponseExpected.getListProducts().get(0).getName(), productResponseActual.getListProducts().get(0).getName());
        assertEquals(productResponseExpected.getListProducts().get(0).getPrice(), productResponseActual.getListProducts().get(0).getPrice());
    }
    
    @Test
    @DisplayName("Test get product by Id")
    void getProductByIdOk() throws ProductException {
        ProductEntity productExpected = CartServiceImplTest.generateProduct();
        Optional<ProductEntity> productExpectedOptional = Optional.of(productExpected);
        
        Mockito.when(productRepository.findById(productExpected.getId())).thenReturn(productExpectedOptional);
        
        ProductEntity productActual = productServiceImpl.getProductById(productExpected.getId()).get();
        
        assertNotNull(productActual);
        assertEquals(productExpected.getName(), productActual.getName());
        assertEquals(productExpected.getDescription(), productActual.getDescription());
        assertEquals(productExpected.getPrice(), productActual.getPrice());
    }
    
    @Test
    @DisplayName("Test get product by Id not found")
    void getProductByIdNotFound() {
        ProductEntity productExpected = CartServiceImplTest.generateProduct();
        
        Mockito.when(productRepository.findById(productExpected.getId())).thenReturn(Optional.empty());
        
        Throwable t = assertThrows(ProductException.class, () -> 
                productServiceImpl.getProductById(productExpected.getId()));
        assertEquals("No se encontró el producto solicitado", t.getMessage());         
    }
    
    @Test
    @DisplayName("Test delete cart")
    void deleteProductOk() throws ProductException {
        Boolean expected = Boolean.TRUE;
        ProductEntity productExpected = CartServiceImplTest.generateProduct();
        Optional<ProductEntity> cartOptional = Optional.of(productExpected);
        
        Mockito.when(productRepository.findById(productExpected.getId())).thenReturn(cartOptional);
        
        Boolean actual = productServiceImpl.deleteProductById(productExpected.getId());
        
        assertNotNull(expected);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test delete product not found")
    void deleteProductNotFound() throws CartException, ProductException {
        ProductEntity productExpected = CartServiceImplTest.generateProduct();
        
        Mockito.when(productRepository.findById(productExpected.getId())).thenReturn(Optional.empty());

        Throwable t = assertThrows(ProductException.class, () -> 
                productServiceImpl.deleteProductById(productExpected.getId()));
        assertEquals("No se encontró el producto solicitado", t.getMessage());        
    }    

    //END TESTS
    
    private ProductsResponse generateProductsResponse() {
        List<ProductEntity> listProducts = CartServiceImplTest.generateProductList();
        
        ProductsResponse productsResponse = 
                ProductsResponse.builder()
                    .listProducts(listProducts)
                    .totalProducts(Long.valueOf(listProducts.size()))
                    .build();
        
        return productsResponse;           
    }
}
