package com.example.FactorITtest.Repository;

import com.example.FactorITtest.Entities.CheckoutEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Xavier Pocchettino
 */
@Repository
public interface CheckoutRepository extends CrudRepository<CheckoutEntity, Long> {

}
