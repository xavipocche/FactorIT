package com.example.FactorITtest.Service.Impl;

import com.example.FactorITtest.Constants.IConstants;
import com.example.FactorITtest.DTO.Request.CartRequest;
import com.example.FactorITtest.DTO.Response.CartStatusResponse;
import com.example.FactorITtest.DTO.Response.CartsResponse;
import com.example.FactorITtest.DTO.Response.CheckoutResponse;
import com.example.FactorITtest.Entities.CartEntity;
import com.example.FactorITtest.Entities.CheckoutEntity;
import com.example.FactorITtest.Entities.ProductEntity;
import com.example.FactorITtest.Entities.UserEntity;
import com.example.FactorITtest.Exceptions.CartException;
import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Exceptions.UserException;
import com.example.FactorITtest.Repository.CartRepository;
import com.example.FactorITtest.Repository.CheckoutRepository;
import com.example.FactorITtest.Repository.ProductRepository;
import com.example.FactorITtest.Service.Interface.CartService;
import com.example.FactorITtest.Service.Interface.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Xavier Pocchettino
 */
@Service
public class CartServiceImpl implements CartService {
    
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;

    @Override
    public CartsResponse getAllCarts() {
        List<CartEntity> listCarts = (List) cartRepository.findAll();
        
        CartsResponse cartsResponse =
                CartsResponse.builder()
                    .listCarts(listCarts)
                    .totalCarts(cartRepository.countCarts())
                    .build();
        
        return cartsResponse;
    }

    @Override
    public Optional<CartEntity> getCartById(Long id) throws CartException {
        Optional<CartEntity> cartOptional = cartRepository.findById(id);
        
        if(cartOptional.isPresent()) {
            return cartOptional;
        } else {
            throw new CartException("No se encontró el carrito solicitado");
        }
    }

    @Override
    public CartEntity saveCart(CartRequest cartRequest) throws CartException, UserException {
        List<ProductEntity> productList = new ArrayList();
        UserEntity userEntity = userService.getUserById(cartRequest.getUserId()).get();
        
        if(cartRequest.getType() == null || cartRequest.getType().trim().isEmpty() || 
            !cartRequest.getType().equals("1")) {
            
            cartRequest.setType(IConstants.COMUN);
        } else if(cartRequest.getType().equals("1")) {
            cartRequest.setType(IConstants.PROMOCIONAL_POR_FECHA_ESPECIAL);
        } else if(cartRequest.getType().equals("2")) {
            cartRequest.setType(IConstants.VIP);
        }
        
        CartEntity cartEntity = 
                CartEntity.builder()
                .user(userEntity)
                .product(productList)
                .type(cartRequest.getType())
                .build();
        
        return cartRepository.save(cartEntity);
    }

    @Override
    public Boolean deleteCartById(Long id) throws CartException {
        Optional<CartEntity> cartOptional = getCartById(id);
        
        if(cartOptional.isPresent()) {
            cartRepository.delete(cartOptional.get());
            return true;
        } else {
            throw new CartException("No se encontró el carrito solicitado para su eliminación");
        }
    }

    @Override
    public CartStatusResponse getCartStatus(Long id) throws CartException {
        Optional<CartEntity> cartOptional = cartRepository.findById(id);
        BigDecimal totalToPay = cartRepository.sumProductPrice(id);
  
        if(cartOptional.isPresent()) {
            
            CartStatusResponse cartStatusResponse = 
                    CartStatusResponse.builder()
                        .cartEntity(cartOptional.get())
                        .totalProducts(cartOptional.get().getProduct().size())
                        .totalToPay(totalToPay)
                        .build();
            
            return cartStatusResponse;
        } else {
            throw new CartException("No se encontró el carrito solicitado para calcular su total a pagar");
        }        
    }

    @Override
    public CartEntity addProduct(Long cartId, Long productId) throws CartException, ProductException {
        Optional<CartEntity> cartOptional = getCartById(cartId);
        Optional<ProductEntity> productOptional = productRepository.findById(productId);
        
        if(productOptional.isPresent()) {
            cartOptional.get().getProduct().add(productOptional.get());
        } else {
            throw new ProductException("No se encontró el producto solicitado para añadirlo al carrito");
        }
        
        return cartRepository.save(cartOptional.get());
    }
    
    @Override
    public CartEntity deleteProduct(Long cartId, Long productId) throws CartException, ProductException {
        Optional<CartEntity> cartOptional = getCartById(cartId);
        Optional<ProductEntity> productOptional = productRepository.findById(productId);
        
        if(productOptional.isPresent()) {
            if(cartOptional.get().getProduct().contains(productOptional.get())) {
                
                cartOptional.get().getProduct().remove(productOptional.get());
                return cartRepository.save(cartOptional.get());
                
            } else {
                throw new CartException("El carrito no contiene el producto solicitado para eliminar");
            }
        } else {
            throw new ProductException("No se encontró el producto para eliminarlo en el carrito");
        }        
    }

    @Override
    public CheckoutResponse payCart(Long cartId) throws CartException, UserException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        
        Optional<CartEntity> cartOptional = getCartById(cartId);
        CartEntity cart = cartOptional.get();
        
        if(cart.getProduct().size() == 0) {
            throw new CartException("El carrito no contiene productos");
        }
        
        CartStatusResponse cartStatus = getCartStatus(cartId);
        
        validateUserVip(cartStatus);
        
        String discount = validateDiscount(cartStatus) + "| Se vació el carrito, pero no fue eliminado";
        
        if(cart.getUser().getBalance().compareTo(cartStatus.getTotalToPay()) != -1) {
            
            userService.addBalance(cart.getUser().getId(), cartStatus.getTotalToPay().negate());
            
            List<ProductEntity> listProducts = new ArrayList();
            listProducts.addAll(cartStatus.getCartEntity().getProduct());
            CheckoutResponse checkoutResponse =
                    CheckoutResponse.builder()
                        .user(cart.getUser())
                        .detail(listProducts)
                        .total(cartStatus.getTotalToPay())
                        .status(IConstants.SUCCESS_SALE)
                        .discount(discount)
                        .build();
            
            CheckoutEntity checkoutEntity =
                CheckoutEntity.builder()
                        .detail(mapper.writeValueAsString(cart.getProduct()))
                        .total(cartStatus.getTotalToPay())
                        .customerFullName(cart.getUser().getName() + " " + cart.getUser().getLastname())
                        .customerId(cart.getUser().getId())
                        .status(checkoutResponse.getStatus())
                        .saleDate(LocalDateTime.now())
                        .build();
            checkoutRepository.save(checkoutEntity);
            
            cartStatus.getCartEntity().getProduct().removeAll(cartStatus.getCartEntity().getProduct());
            cartRepository.save(cartStatus.getCartEntity());
            
            return checkoutResponse;
        } else {
            CheckoutEntity checkoutEntity =
                CheckoutEntity.builder()
                        .detail(mapper.writeValueAsString(cart.getProduct()))
                        .total(cartStatus.getTotalToPay())
                        .customerFullName(cart.getUser().getName() + " " + cart.getUser().getLastname())
                        .customerId(cart.getUser().getId())
                        .status(IConstants.CANCELED_INSUFFICIENT_BALANCE)
                        .saleDate(LocalDateTime.now())
                        .build();            
            checkoutRepository.save(checkoutEntity);
            
            cartRepository.delete(cart);
            
            throw new CartException("El usuario no tiene saldo suficiente para pagar el carrito. El carrito fue eliminado");
        }
    }
    
    public void validateUserVip(CartStatusResponse cartStatusResponse) throws UserException {
        if(!cartStatusResponse.getCartEntity().getType().equalsIgnoreCase(IConstants.PROMOCIONAL_POR_FECHA_ESPECIAL)) {
            if(userService.getTotalSpendInActualMonth(cartStatusResponse.getCartEntity().getUser().getId()).compareTo(new BigDecimal("10000")) != -1) {
                cartStatusResponse.getCartEntity().setType(IConstants.VIP);
            }else {
                cartStatusResponse.getCartEntity().setType(IConstants.COMUN);
            }            
        } 
    }
    
    private String validateDiscount(CartStatusResponse cartStatusResponse) {
        BigDecimal previousPrice = cartStatusResponse.getTotalToPay();
        String discountApliedMessage = "";
        
        if(cartStatusResponse.getCartEntity().getProduct().size() == 4) {
            cartStatusResponse.setTotalToPay(cartStatusResponse.getTotalToPay().subtract(cartStatusResponse.getTotalToPay().multiply(new BigDecimal(0.25))));
            
            discountApliedMessage = "Se aplicó un descuento del 25% por comprar 4 productos:" + 
                   " PRECIO INICIAL: " + previousPrice +
                   " PRECIO FINAL: " + cartStatusResponse.getTotalToPay();
        
        }else if(cartStatusResponse.getCartEntity().getProduct().size() > 10) {
            if(cartStatusResponse.getCartEntity().getType().equalsIgnoreCase(IConstants.COMUN)) {
                cartStatusResponse.setTotalToPay(cartStatusResponse.getTotalToPay().subtract(new BigDecimal(100)));
                
                discountApliedMessage = "Se aplicó un descuento de 100 por comprar más de 10 productos y ser carro: " + IConstants.COMUN +
                       " PRECIO INICIAL: " + previousPrice +
                       " PRECIO FINAL: " + cartStatusResponse.getTotalToPay();
                
            }else if(cartStatusResponse.getCartEntity().getType().equalsIgnoreCase(IConstants.PROMOCIONAL_POR_FECHA_ESPECIAL)) {
                cartStatusResponse.setTotalToPay(cartStatusResponse.getTotalToPay().subtract(new BigDecimal(300)));
                
                discountApliedMessage = "Se aplicó un descuento de 300 por comprar más de 10 productos y ser carro: " + IConstants.PROMOCIONAL_POR_FECHA_ESPECIAL +
                       " PRECIO INICIAL: " + previousPrice +
                       " PRECIO FINAL: " + cartStatusResponse.getTotalToPay();                
            
            }else if(cartStatusResponse.getCartEntity().getType().equalsIgnoreCase(IConstants.VIP)) {
                BigDecimal discountTotal = 
                        cartRepository.getMinPriceFromCart(cartStatusResponse.getCartEntity().getUser().getId()).add(new BigDecimal(500));
                
                cartStatusResponse.setTotalToPay(cartStatusResponse.getTotalToPay().subtract(discountTotal));
                
                discountApliedMessage = "Se aplicó un descuento de 500 por comprar más de 10 productos y ser " + IConstants.VIP + " además se bonificó el producto más barato" +
                       " PRECIO INICIAL: " + previousPrice +
                       " PRECIO FINAL: " + cartStatusResponse.getTotalToPay();   
            }   
        }else {
            discountApliedMessage = "No se aplicaron descuentos";    
        }
        
        return discountApliedMessage;
    }
}
