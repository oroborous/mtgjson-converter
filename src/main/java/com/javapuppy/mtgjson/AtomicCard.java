package com.javapuppy.mtgjson;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@NoArgsConstructor
public class AtomicCard {
    private String asciiName;
    private String[] colorIdentity;
    private String[] colors;
    private int edhrecRank;
    private boolean isReserved;
    private String[] keywords;
    private String manaCost;
    private float manaValue;
    private String power;
    private String[] printings;
    private String[] subtypes;
    private String[] supertypes;
    private String text;
    private String toughness;
    private String[] types;

    @Override
    public String toString() {
        return "Card{" +
                "asciiName='" + asciiName + '\'' +
                ", colorIdentity=" + Arrays.toString(colorIdentity) +
                ", colors=" + Arrays.toString(colors) +
                ", edhrecRank=" + edhrecRank +
                ", isReserved=" + isReserved +
                ", keywords=" + Arrays.toString(keywords) +
                ", manaCost='" + manaCost + '\'' +
                ", manaValue=" + manaValue +
                ", power='" + power + '\'' +
                ", printings=" + Arrays.toString(printings) +
                ", subtypes=" + Arrays.toString(subtypes) +
                ", supertypes=" + Arrays.toString(supertypes) +
                ", text='" + text + '\'' +
                ", toughness='" + toughness + '\'' +
                ", types=" + Arrays.toString(types) +
                '}';
    }
}
