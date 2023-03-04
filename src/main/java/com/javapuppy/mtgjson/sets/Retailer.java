package com.javapuppy.mtgjson.sets;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Retailer {
    private String currency;
    private RetailPrices retail;
}
