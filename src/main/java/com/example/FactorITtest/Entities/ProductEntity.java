package com.example.FactorITtest.Entities;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
@Table(name = "product")
public class ProductEntity {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Product´s name cannot be null")
    @Size(min = 3, max = 50)
    @Column(name = "name")
    private String name;
    
    @NotEmpty(message = "Description may not null")
    @Size(min = 3, max = 200)
    @Column(name = "description")
    private String description;
    
    @NotEmpty(message = "Product´s price cannot be null")
    @Column(name = "price")
    private BigInteger price;
}
