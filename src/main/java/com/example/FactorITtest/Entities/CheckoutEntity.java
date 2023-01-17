package com.example.FactorITtest.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Xavier Pocchettino
 */
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sales")
public class CheckoutEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    @Column(name = "id")
    private Long id;
    
    @Column(name = "detail", columnDefinition = "longtext")
    private String detail;
    
    @Column(name = "total")
    private BigDecimal total;
    
    @Column(name = "customerFullName")
    private String customerFullName;
    
    @Column(name = "customerId")
    private Long customerId;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "saleDate")
    private LocalDateTime saleDate;
}
