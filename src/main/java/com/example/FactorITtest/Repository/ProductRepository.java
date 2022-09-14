package com.example.FactorITtest.Repository;

import com.example.FactorITtest.Entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Xavier Pocchettino
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

}
