package com.javapuppy.mtgjson.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetFile {
    private Meta meta;
    private CardSet data;
}
