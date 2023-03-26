package com.javapuppy.mtgjson.entity.setfile;

import com.javapuppy.mtgjson.entity.Meta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SetFile {
    private Meta meta;
    private List<SetDetails> data;
}
