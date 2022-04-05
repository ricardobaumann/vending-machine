package com.github.ricbau.vendingmachine.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ElementCollection
    @CollectionTable(name = "product_sellers",
            joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "seller_id")
    private List<String> sellerIds;

    @Column(name = "owner_id")
    private String owner;
}
