package com.example.FactorITtest.Repository;

import com.example.FactorITtest.Entities.CartEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Xavier Pocchettino
 */
@Repository
public interface CartRepository extends CrudRepository<CartEntity, Long> {
    
    @Query(value = "SELECT COUNT(*) FROM cart", nativeQuery = true)
    Long countCarts();
}
