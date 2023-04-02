package com.javapuppy.mtgjson.entity.setfile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetDetails {
    private String code;
    private String name;
    private String releaseDate;
    private int cardsphereSetId;
    private int tcgplayerGroupId;
    private int totalSetSize;

    public static String getFileHeader() {
        return String.format("%s,%s,%s,%s,%s,%s",
                "code",
                "name",
                "releaseDate",
                "cardsphereSetId",
                "tcgplayerGroupId",
                "totalSetSize");
    }

    @Override
    public String toString() {
        return String.format("%s,\"%s\",%s,%d,%d,%d",
                code,
                name,
                releaseDate,
                cardsphereSetId,
                tcgplayerGroupId,
                totalSetSize);
    }
}
