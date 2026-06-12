package org.example;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    public Order() {
        // Required by JPA and used in repository tests.
    }

    public Order(Long id, Double totalAmount, Currency currency) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }

}
