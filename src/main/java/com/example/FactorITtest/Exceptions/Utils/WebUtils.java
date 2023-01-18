package com.example.FactorITtest.Exceptions.Utils;

import com.example.FactorITtest.Exceptions.ProductException;
import com.example.FactorITtest.Exceptions.UserException;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Xavier Pocchettino
 */
public class WebUtils {
    
    private static final String ERROR_CODE = "Error-Code";
    private static final String ERROR_MESSAGE = "Error-Message";
    
    public static ResponseEntity generateResponseEntityFromException(String code, Exception e) {
        String error;
        if(e.getMessage().equals(ErrorTypesConstants.PRODUCT_ADDED_ERROR)) {
            error = "El producto ya se encuentra agregado en un carrito";
        } else if(e.getMessage().equals(ErrorTypesConstants.DELETE_PRODUCT_ADDED_ERROR) && e instanceof ProductException) {
            error = "No puede eliminar un producto que está dentro en un carrito";
        } else if(e.getMessage().equals(ErrorTypesConstants.DELETE_PRODUCT_ADDED_ERROR) && e instanceof UserException) {
            error = "No puede eliminar un usuario que tiene un carrito asignado";
        } else {
            error = e.getMessage();
        }
        final String errorMessage = error;
        return ResponseEntity.unprocessableEntity().headers(headers -> {
                headers.add(ERROR_CODE, code);
                headers.add(ERROR_MESSAGE, errorMessage);
            }).body(error);
    }    
}
