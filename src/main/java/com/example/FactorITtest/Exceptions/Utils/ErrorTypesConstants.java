package com.example.FactorITtest.Exceptions.Utils;

/**
 *
 * @author Xavier Pocchettino
 */
public interface ErrorTypesConstants {
    String PRODUCT_ADDED_ERROR = "could not execute statement; SQL [n/a]; constraint [cart_product.UK_23mn69dwnudyul90odbyx1oia]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
    String DELETE_PRODUCT_ADDED_ERROR = "could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
}