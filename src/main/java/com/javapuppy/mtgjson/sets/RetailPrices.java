package com.javapuppy.mtgjson.sets;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class RetailPrices {
    private Map<String, Double> normal;
}
