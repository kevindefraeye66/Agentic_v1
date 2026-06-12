package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDto {

    private final Long id;
    private final Double totalAmount;
    private final Currency currency;
}


