package com.example.FactorITtest.Repository;

import com.example.FactorITtest.Entities.UserEntity;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Xavier Pocchettino
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    
    @Query(value = "SELECT COUNT(*) FROM user", nativeQuery = true)
    Long countUsers();
    
    @Query(value = "SELECT SUM(s.total) FROM sales s WHERE s.customer_id = :idUser "
        + "AND s.status = 'SUCCESS' AND MONTH(s.sale_date) = MONTH(current_date());", nativeQuery = true)
    BigDecimal getTotalSpendInActualMonth(@Param("idUser") Long idUser);
    
    @Query(value = "SELECT s.id, s.name, s.lastname, s.email, s.balance FROM (SELECT u.id, u.name, u.lastname ,u.email, u.balance, SUM(s.total) AS 'total_spent' FROM sales s INNER JOIN user u ON u.id = s.customer_id WHERE s.status = 'SUCCESS' GROUP BY s.customer_id) AS s WHERE s.total_spent > 10000", nativeQuery = true)
    List<UserEntity> getVipUsers();
    
    
}
