package com.github.ricbau.vendingmachine.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "sales")
public class SaleEntity {
    @Id
    private String id;

    @ManyToOne
    private ProductEntity product;

    private String username;

    private Integer amount;

}
