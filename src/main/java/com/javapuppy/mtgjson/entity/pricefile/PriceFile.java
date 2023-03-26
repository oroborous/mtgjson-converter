package com.javapuppy.mtgjson.entity.pricefile;

import com.javapuppy.mtgjson.entity.Meta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PriceFile {
    private Meta meta;
    private Map<String, PriceHistory> data; // market is String, e.g. "cardkingdom"
}
