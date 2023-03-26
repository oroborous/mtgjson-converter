package com.javapuppy.mtgjson.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PriceHistory {
    private Map<String, Retailer> mtgo;
    private Map<String, Retailer> paper;
}
