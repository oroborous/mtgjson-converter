package com.javapuppy.mtgjson.entity.pricefile;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PriceHistory {
    private Map<String, Retailer> paper;
}
