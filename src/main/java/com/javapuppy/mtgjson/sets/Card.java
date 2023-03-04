package com.javapuppy.mtgjson.sets;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {
    private String artist;
    private String asciiName;
    private String[] attractionLights;
    private String[] availability;
    private Double avgPrice;
    private String[] boosterTypes;
    private String borderColor;
    private String[] cardParts;
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
    private String[] finishes;
    private String flavorName;
    private String flavorText;
    private String[] frameEffects;
    private String frameVersion;
    private String hand;
    private Boolean hasAlternativeDeckLimit;
    private Boolean hasContentWarning;
    private Boolean hasFoil;
    private Boolean hasNonFoil;
    private Boolean isAlternative;
    private Boolean isFullArt;
    private Boolean isFunny;
    private Boolean isOnlineOnly;
    private Boolean isOversized;
    private Boolean isPromo;
    private Boolean isRebalanced;
    private Boolean isReprint;
    private Boolean isStarter;
    private Boolean isStorySpotlight;
    private Boolean isTextless;
    private Boolean isTimeshifted;
    private String[] keywords;
    private String language;
    private String layout;
    private String life;
    private String loyalty;
    private String manaCost;
    private float manaValue;
    private String name;
    private String number;
    private String[] originalPrintings;
    private String originalReleaseDate;
    private String originalText;
    private String originalType;
    private String power;
    private String[] printings;
    private String[] promoTypes;
    private String rarity;
    private String[] rebalancedPrintings;
    private String securityStamp;
    private String setCode;
    private String setName;
    private String side;
    private String signature;
    private String[] subsets;
    private String[] subtypes;
    private String[] supertypes;
    private String text;
    private String toughness;
    private String type;
    private String[] types;
    private String uuid;
    private String[] variations;
    private String watermark;

    public static String getFileHeader() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                "uuid",
                "title",
                "color",
                "numColors",
                "type",
                "edhrecRank",
                "avgPrice",
                "manaValue",
                "power",
                "toughness",
                "setName",
                "setCode",
                "releaseDate");
    }

    @Override
    public String toString() {
        return String.format("%s,\"%s\",%s,%d,%s,%d,%f,%f,%s,%s,\"%s\",%s,%s",
                uuid,
                name,
                colors.length == 1 ? colors[0] : (colors.length == 0 ? "Colorless" : "Multicolor"),
                colors.length,
                type,
                edhrecRank,
                avgPrice,
                manaValue,
                power,
                toughness,
                setName,
                setCode,
                originalReleaseDate);
    }
}
