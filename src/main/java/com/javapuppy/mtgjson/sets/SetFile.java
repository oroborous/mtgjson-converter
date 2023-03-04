package com.javapuppy.mtgjson.sets;

import com.javapuppy.mtgjson.Meta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetFile {
    private Meta meta;
    private CardSet data;
}
