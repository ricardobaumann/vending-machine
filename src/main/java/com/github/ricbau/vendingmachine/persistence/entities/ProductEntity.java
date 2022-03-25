package com.github.ricbau.vendingmachine.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "products")
public class ProductEntity {
    @Id
    private String id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount_available")
    private Integer amountAvailable;

    @Column(name = "cost_in_cents")
    private Integer costInCents;
}
