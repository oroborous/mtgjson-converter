package com.javapuppy.mtgjson;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class AtomicCards {
    private Meta meta;
    private Map<String, AtomicCard[]> data;
}
