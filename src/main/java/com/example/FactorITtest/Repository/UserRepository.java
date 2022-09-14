package com.example.FactorITtest.Repository;

import com.example.FactorITtest.Entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Xavier Pocchettino
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    
    @Query(value = "SELECT COUNT(*) FROM user", nativeQuery = true)
    Long countUsers();
}
