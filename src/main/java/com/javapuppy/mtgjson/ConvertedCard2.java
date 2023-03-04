package com.javapuppy.mtgjson;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ConvertedCard2 {
        private String title;
        private String color;
        private int numColors;
        private String type;
        private int edhrecRank;
        private boolean isReserved;
        private boolean wasReprinted;
        private float manaValue;
        private int power;
        private int toughness;
        private String firstPrintingSetName;
        private String firstPrintingSetCode;
        private LocalDate firstPrintingDate;
        private boolean onlineOnly;

        public static String getFileHeader() {
            return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                    "title",
                    "color",
                    "numColors",
                    "type",
                    "edhrecRank",
                    "reserved",
                    "reprinted",
                    "manaValue",
                    "power",
                    "toughness",
                    "firstPrintingSetName",
                    "firstPrintingSetCode",
                    "firstPrintingDate",
                    "onlineOnly");
        }

        @Override
        public String toString() {
            return String.format("\"%s\",%s,%d,%s,%d,%b,%b,%f,%d,%d,\"%s\",%s,%s,%b",
                    title,
                    color,
                    numColors,
                    type,
                    edhrecRank,
                    isReserved,
                    wasReprinted,
                    manaValue,
                    power,
                    toughness,
                    firstPrintingSetName,
                    firstPrintingSetCode,
                    firstPrintingDate,
                    onlineOnly);
        }
}
