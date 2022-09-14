package com.example.FactorITtest.Repository;

import com.example.FactorITtest.Entities.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Xavier Pocchettino
 */
@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    
    @Query(value = "SELECT COUNT(*) FROM product", nativeQuery = true)
    Long countProducts();
}
