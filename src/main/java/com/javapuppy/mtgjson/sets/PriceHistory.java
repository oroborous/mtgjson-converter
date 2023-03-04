package com.javapuppy.mtgjson.sets;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PriceHistory {
    private Map<String, Retailer> mtgo;
    private Map<String, Retailer> paper;
}
