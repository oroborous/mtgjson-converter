package com.javapuppy.mtgjson;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ConvertedCard {
    private String title;
    private boolean redIdentity;
    private boolean greenIdentity;
    private boolean whiteIdentity;
    private boolean blackIdentity;
    private boolean blueIdentity;
    private int numColors;
    private boolean instantType;
    private boolean artifactType;
    private boolean planeswalkerType;
    private boolean sorceryType;
    private boolean creatureType;
    private boolean enchantmentType;
    private boolean landType;
    private int edhrecRank;
    private boolean isReserved;
    private boolean wasReprinted;
    private float manaValue;
    private int power;
    private int toughness;
    private String firstPrintingSetName;
    private String firstPrintingSetCode;
    private LocalDate firstPrintingDate;

    public static String getFileHeader() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                "title",
                "red",
                "green",
                "white",
                "black",
                "blue",
                "numColors",
                "instant",
                "artifact",
                "planeswalker",
                "sorcery",
                "creature",
                "enchantment",
                "land",
                "edhrecRank",
                "reserved",
                "reprinted",
                "manaValue",
                "power",
                "toughness",
                "firstPrintingSetName",
                "firstPrintingSetCode",
                "firstPrintingDate");
    }

    @Override
    public String toString() {
        return String.format("\"%s\",%b,%b,%b,%b,%b,%d,%b,%b,%b,%b,%b,%b,%b,%d,%b,%b,%f,%d,%d,%s,%s,%s",
                title,
                redIdentity,
                greenIdentity,
                whiteIdentity,
                blackIdentity,
                blueIdentity,
                numColors,
                instantType,
                artifactType,
                planeswalkerType,
                sorceryType,
                creatureType,
                enchantmentType,
                landType,
                edhrecRank,
                isReserved,
                wasReprinted,
                manaValue,
                power,
                toughness,
                firstPrintingSetName,
                firstPrintingSetCode,
                firstPrintingDate);
    }
}
