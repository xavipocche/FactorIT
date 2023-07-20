package com.example.FactorITtest;

import com.example.FactorITtest.Constants.IConstants;
import com.example.FactorITtest.DTO.Response.CartStatusResponse;
import com.example.FactorITtest.DTO.Response.CartsResponse;
import com.example.FactorITtest.Entities.CartEntity;
import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Entities.UserEntity;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Exceptions.UserException;
import com.example.FactorITtest.Repository.CartRepository;
import com.example.FactorITtest.Repository.ProductRepository;
import com.example.FactorITtest.Repository.UserRepository;
import com.example.FactorITtest.Service.Impl.CartServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Xavier Pocchettino
 */
@ExtendWith(SpringExtension.class)
public class CartServiceImplTest {
    
    @InjectMocks
    private CartServiceImpl cartServiceImpl;
    
    @Mock
    private CartRepository cartRepository;
    
    @Mock 
    private ProductRepository productRepository;
    
    @Mock 
    private UserRepository userRepository;    

    @Test
    @DisplayName("Test get all carts OK")
    void getAllCartsOk() {
        CartsResponse cartsResponseExpected = generateCartsResponse();
        
        Mockito.when(cartRepository.findAll()).thenReturn(cartsResponseExpected.getListCarts());
        Mockito.when(cartRepository.countCarts()).thenReturn(cartsResponseExpected.getTotalCarts());
        
        CartsResponse cartsResponseActual = cartServiceImpl.getAllCarts();
        
        assertNotNull(cartsResponseActual);
        assertEquals(cartsResponseExpected.getListCarts(), cartsResponseActual.getListCarts());
        assertEquals(cartsResponseExpected.getTotalCarts(), cartsResponseActual.getTotalCarts());
    }
    
    @Test
    @DisplayName("Test get one cart by Id OK")
    void getCartByIdOk() throws CartException {
        CartEntity cartExpected = generateCart();
        Optional<CartEntity> cartOptional = Optional.of(cartExpected);
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(cartOptional);
        
        CartEntity cartActual = cartServiceImpl.getCartById(cartExpected.getId()).get();
        
        assertNotNull(cartActual);
        assertEquals(cartExpected.getId(), cartActual.getId());
        assertEquals(cartExpected.getProduct(), cartActual.getProduct());
        assertEquals(cartExpected.getType(), cartActual.getType());
        assertEquals(cartExpected.getUser(), cartActual.getUser());
    }
    
    @Test
    @DisplayName("Test cart not found")
    void getCartNotFound() throws CartException {
        CartEntity cartExpected = generateCart();
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(Optional.empty());
        
        Throwable t = assertThrows(CartException.class, () -> 
                cartServiceImpl.getCartById(cartExpected.getId()));
        assertEquals("No se encontró el carrito solicitado", t.getMessage());
    }
    
    @Test
    @DisplayName("Test delete cart")
    void deleteCartOk() throws CartException {
        Boolean expected = Boolean.TRUE;
        CartEntity cartExpected = generateCart();
        Optional<CartEntity> cartOptional = Optional.of(cartExpected);
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(cartOptional);
        
        Boolean actual = cartServiceImpl.deleteCartById(cartExpected.getId());
        
        assertNotNull(expected);
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Test delete fail")
    void deleteCartFail() throws CartException {
        CartEntity cartExpected = generateCart();
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(Optional.empty());
        
        Throwable t = assertThrows(CartException.class, () -> 
                cartServiceImpl.getCartById(cartExpected.getId()));
        assertEquals("No se encontró el carrito solicitado", t.getMessage());
    }
    
    @Test
    @DisplayName("Test get cart Status")
    void getCartStatus() throws CartException {
        CartStatusResponse cartStatusResponseExpected = generateCartStatusResponse();
        CartEntity cartExpected = generateCart();
        Optional<CartEntity> cartOptional = Optional.of(cartExpected);

        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(cartOptional);
        Mockito.when(cartRepository.sumProductPrice(cartExpected.getId())).thenReturn(cartExpected.getProduct().get(0).getPrice());
        
        CartStatusResponse cartStatusResponseActual = cartServiceImpl.getCartStatus(cartExpected.getId());
        
        assertNotNull(cartStatusResponseActual);
        assertEquals(cartStatusResponseExpected.getCartEntity().getId(), cartStatusResponseActual.getCartEntity().getId());
        assertEquals(cartStatusResponseExpected.getCartEntity().getType(), cartStatusResponseActual.getCartEntity().getType());
        assertEquals(cartStatusResponseExpected.getTotalProducts(), cartStatusResponseActual.getTotalProducts());
        assertEquals(cartStatusResponseExpected.getTotalToPay(), cartStatusResponseActual.getTotalToPay());
    }
    
    @Test
    @DisplayName("Test get cart Status Error")
    void getCartStatusFail() throws CartException {
        CartEntity cartExpected = generateCart();

        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(Optional.empty());
        
        Throwable t = assertThrows(CartException.class, () -> 
                cartServiceImpl.getCartStatus(cartExpected.getId()));
        assertEquals("No se encontró el carrito solicitado para calcular su total a pagar", t.getMessage());
    }

    @Test
    @DisplayName("Test add product cart not found")
    void addProductCartNotFound() throws CartException, ProductException {
        CartEntity cartExpected = generateCart();
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(Optional.empty());

        Throwable t = assertThrows(CartException.class, () -> 
                cartServiceImpl.addProduct(cartExpected.getId(), cartExpected.getProduct().get(0).getId()));
        assertEquals("No se encontró el carrito solicitado", t.getMessage());        
    }
    
    @Test
    @DisplayName("Test add product not found")
    void addProductNotFound() throws CartException, ProductException {
        CartEntity cartExpected = generateCart();
        Optional<CartEntity> cartOptional = Optional.of(cartExpected);
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(cartOptional);
        Mockito.when(productRepository.findById(cartExpected.getProduct().get(0).getId())).thenReturn(Optional.empty());

        Throwable t = assertThrows(ProductException.class, () -> 
                cartServiceImpl.addProduct(cartExpected.getId(), cartExpected.getProduct().get(0).getId()));
        assertEquals("No se encontró el producto solicitado para añadirlo al carrito", t.getMessage());        
    }

    @Test
    @DisplayName("Test delete product from cart not found")
    void deleteProductCartNotFound() {
        CartEntity cartExpected = generateCart();
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(Optional.empty());

        Throwable t = assertThrows(CartException.class, () -> 
                cartServiceImpl.deleteProduct(cartExpected.getId(), cartExpected.getProduct().get(0).getId()));
        assertEquals("No se encontró el carrito solicitado", t.getMessage());          
    }
    
    @Test
    @DisplayName("Test delete product not found")
    void deleteProductNotFound() throws CartException, ProductException {
        CartEntity cartExpected = generateCart();
        Optional<CartEntity> cartOptional = Optional.of(cartExpected);
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(cartOptional);
        Mockito.when(productRepository.findById(cartExpected.getProduct().get(0).getId())).thenReturn(Optional.empty());

        Throwable t = assertThrows(ProductException.class, () -> 
                cartServiceImpl.deleteProduct(cartExpected.getId(), cartExpected.getProduct().get(0).getId()));
        assertEquals("No se encontró el producto para eliminarlo en el carrito", t.getMessage());        
    }

    @Test
    @DisplayName("Test delete product from cart not found")
    void deleteProductFromCartNotFound() throws CartException, ProductException {
        CartEntity cartExpected = generateCart();
        Optional<CartEntity> cartOptional = Optional.of(cartExpected);
        
        ProductEntity productNew =
                ProductEntity.builder()
                    .name("product")
                    .description("new product")
                    .price(BigDecimal.ONE)
                    .build();
        
        Optional<ProductEntity> productOptional = Optional.of(productNew);
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(cartOptional);
        Mockito.when(productRepository.findById(cartExpected.getProduct().get(0).getId())).thenReturn(productOptional);

        Throwable t = assertThrows(CartException.class, () -> 
                cartServiceImpl.deleteProduct(cartExpected.getId(), cartExpected.getProduct().get(0).getId()));
        assertEquals("El carrito no contiene el producto solicitado para eliminar", t.getMessage());        
    }

    @Test
    @DisplayName("Test pay cart OK")
    void payCartOk() throws CartException, UserException, JsonProcessingException {
        CartEntity cartExpected = generateCart();
        Optional<CartEntity> cartOptional = Optional.of(cartExpected);
        cartExpected.getProduct().removeAll(cartExpected.getProduct());
        
        Mockito.when(cartRepository.findById(cartExpected.getId())).thenReturn(cartOptional);
       
        Throwable t = assertThrows(CartException.class, () -> 
                cartServiceImpl.payCart(cartExpected.getId()));
        assertEquals("El carrito no contiene productos", t.getMessage());        
    }
    
    //END TESTS
    
    private CartStatusResponse generateCartStatusResponse() {
        CartEntity cart = generateCart();
        CartStatusResponse cartStatusResponse = 
                CartStatusResponse.builder()
                    .cartEntity(cart)
                    .totalProducts(cart.getProduct().size())
                    .totalToPay(cart.getProduct().get(0).getPrice())
                    .build();
        
        return cartStatusResponse;
    }
    
    
    private CartsResponse generateCartsResponse() {
        List<CartEntity> cartList = generateCartList();
        
        CartsResponse cartsResponse = 
                CartsResponse.builder()
                    .listCarts(cartList)
                    .totalCarts(Long.valueOf(cartList.size()))
                    .build();
        
        return cartsResponse;
    }
    
    private List<CartEntity> generateCartList() {
        List<CartEntity> cartList = new ArrayList();
        
        cartList.add(generateCart());
        
        return cartList;
    }
    
    private CartEntity generateCart() {
        CartEntity cart = 
                CartEntity.builder()
                    .user(generateUser())
                    .product(generateProductList())
                    .type(IConstants.COMUN)
                    .build();
        return cart;
    }    
    
    public static List<ProductEntity> generateProductList() {
        List<ProductEntity> productList = new ArrayList();
        productList.add(generateProduct());
        
        return productList;
    }
    
    public static ProductEntity generateProduct() {
        ProductEntity product =
                ProductEntity.builder()
                    .name("producto")
                    .description("description")
                    .price(new BigDecimal("100"))
                    .build();
        return product;
    }

    public static UserEntity generateUser() {
        UserEntity user =
                UserEntity.builder()
                    .name("user")
                    .lastname("user")
                    .email("user@mail.com")
                    .balance(new BigDecimal("10000"))
                    .build();
        return user;            
    }    
}
