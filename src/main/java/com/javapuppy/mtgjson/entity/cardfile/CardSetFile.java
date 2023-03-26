package com.javapuppy.mtgjson.entity.cardfile;

import com.javapuppy.mtgjson.entity.Meta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardSetFile {
    private Meta meta;
    private CardSet data;
}
