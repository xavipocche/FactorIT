package com.example.FactorITtest.Exceptions.Utils;

import com.example.FactorITtest.Exceptions.UserException;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Xavier Pocchettino
 */
public class WebUtils {
    
    public static ResponseEntity generateResponseEntityFromException(String code, Exception e) {
        if (e instanceof UserException) {
            return ResponseEntity.unprocessableEntity().headers(headers -> {
                headers.add("ERROR_CODE", code);
                headers.add("ERROR_MESSAGE", e.getMessage());
            }).body(null);
        } else {
            return null;
        }
    }    
}
