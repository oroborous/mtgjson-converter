package com.javapuppy.mtgjson.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Retailer {
    private String currency;
    private RetailPrices retail;
    private RetailPrices buylist;
}
