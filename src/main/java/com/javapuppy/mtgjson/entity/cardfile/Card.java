package com.javapuppy.mtgjson.entity.cardfile;

import com.javapuppy.mtgjson.SetConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {
    private String artist;
    private String asciiName;
    private Double avgRetailPrice;
    private Double avgBuylistPrice;
    private String borderColor;
    private String[] colorIdentity;
    private String[] colorIndicator;
    private String[] colors;
    private Double convertedManaCost;
    private Integer edhrecRank;
    private Integer edhrecSaltiness;
    private Double faceConvertedManaCost;
    private String faceFlavorName;
    private Double faceManaValue;
    private String faceName;
    private String hand;
    private Boolean hasAlternativeDeckLimit;
    private Boolean hasContentWarning;
    private Boolean hasFoil;
    private Boolean hasNonFoil;
    private Boolean isAlternative;
    private Boolean isFullArt;
    private Boolean isFunny;
    private Boolean isOnlineOnly;
    private Boolean isPromo;
    private Boolean isRebalanced;
    private Boolean isReprint;
    private Boolean isStarter;
    private String[] keywords;
    private String language;
    private String life;
    private String manaCost;
    private float manaValue;
    private String name;
    private String number;
    private String[] originalPrintings;
    private String originalReleaseDate;
    private String power;
    private String[] printings;
    private String rarity;
    private String setCode;
    private String side;
    private String signature;
    private String[] subsets;
    private String toughness;
    private String type;
    private String uuid;

    public static String getFileHeader() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                "uuid",
                "title",
                "color",
                "numColors",
                "type",
                "edhrecRank",
                "avgRetailPrice",
                "avgBuylistPrice",
                "manaValue",
                "power",
                "toughness",
                "setName",
                "setCode",
                "releaseDate",
                "rarity",
                "artist");
    }

    @Override
    public String toString() {
        return String.format("%s,\"%s\",%s,%d,%s,%d,%f,%f,%f,%s,%s,\"%s\",%s,%s,%s,%s",
                uuid,
                name,
                colors.length == 1 ? colors[0] : (colors.length == 0 ? "Colorless" : "Multicolor"),
                colors.length,
                type,
                edhrecRank,
                avgRetailPrice,
                avgBuylistPrice,
                manaValue,
                power,
                toughness,
                SetConverter.setCodeToNameMap.getOrDefault(setCode, setCode),
                setCode,
                originalReleaseDate,
                rarity,
                artist);
    }
}
