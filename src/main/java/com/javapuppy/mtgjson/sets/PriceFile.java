package com.javapuppy.mtgjson.sets;

import com.javapuppy.mtgjson.Meta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PriceFile {
    private Meta meta;
    private Map<String, PriceHistory> data;
}
