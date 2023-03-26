package com.javapuppy.mtgjson.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class RetailPrices {
    // ignoring foil prices (non-foil is "normal")
    private Map<String, Double> normal;
}
