package com.salesSystem.domain.models;

import com.salesSystem.domain.dtos.product.ProductRegisterDto;
import com.salesSystem.domain.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "products")
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = true)
    private String productDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory productCategory;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Integer balance;

    @Column(nullable = false)
    private BigDecimal price;


    public Product(ProductRegisterDto newProduct) {
        this.productName = newProduct.productName();
        if (newProduct.productDescription() != null) {
            this.productDescription = newProduct.productDescription();
        }

        this.productCategory = newProduct.productCategory();

        this.brand = newProduct.brand();

        this.balance = newProduct.balance();

        this.price = newProduct.price();

    }
}
