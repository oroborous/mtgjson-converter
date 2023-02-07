package com.javapuppy.mtgjson;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CardSet {
    private String name;
    private String code;
    private int baseSetSize;
    private String block;
    private String language;
    private boolean isForeignOnly;
    private boolean isOnlineOnly;
    private LocalDate releaseDate;
    private int totalSetSize;
    private String type;
}
