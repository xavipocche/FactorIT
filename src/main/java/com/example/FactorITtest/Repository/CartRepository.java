package com.example.FactorITtest.Repository;

import com.example.FactorITtest.Entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Xavier Pocchettino
 */
@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

}
