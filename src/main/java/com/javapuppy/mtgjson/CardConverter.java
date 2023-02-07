package com.javapuppy.mtgjson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class CardConverter {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());

        CardSets cardSetsObj = mapper.readValue(CardConverter.class.getResourceAsStream("AllPrintings.json"),
                CardSets.class);
        Map<String, CardSet> cardSets = cardSetsObj.getData();
//        System.out.println(cardSets.size());

        List<CardSet> convertedSets = cardSets.values().stream().toList();

        AtomicCards atomicCardsObj = mapper.readValue(CardConverter.class.getResourceAsStream("AtomicCards.json"),
                AtomicCards.class);
        Map<String, AtomicCard[]> atomicCards = atomicCardsObj.getData();
//        System.out.println(atomicCards.size());


        List<ConvertedCard> convertedCards = new ArrayList<>();

        List<String> typesToKeep = Arrays.asList("sorcery", "enchantment", "land", "instant",
                "creature", "planeswalker", "artifact");

        atomicCards.forEach((name, atomicCardList) -> {
            Arrays.stream(atomicCardList).forEach(atomicCard -> {
                ConvertedCard cc = new ConvertedCard();

                Arrays.stream(atomicCard.getTypes()).forEach(type -> {
                    if (typesToKeep.contains(type.toLowerCase())) {
                        convertedCards.add(cc);

                        if (type.equalsIgnoreCase("sorcery"))
                            cc.setSorceryType(true);
                        else if (type.equalsIgnoreCase("enchantment"))
                            cc.setEnchantmentType(true);
                        else if (type.equalsIgnoreCase("land"))
                            cc.setLandType(true);
                        else if (type.equalsIgnoreCase("instant"))
                            cc.setInstantType(true);
                        else if (type.equalsIgnoreCase("creature"))
                            cc.setCreatureType(true);
                        else if (type.equalsIgnoreCase("planeswalker"))
                            cc.setPlaneswalkerType(true);
                        else if (type.equalsIgnoreCase("artifact"))
                            cc.setArtifactType(true);

                        if (atomicCard.getAsciiName() == null ||
                                atomicCard.getAsciiName().equals("null")) {
                            cc.setTitle(name);
                        } else {
                            cc.setTitle(atomicCard.getAsciiName());
                        }

                        cc.setTitle(cc.getTitle().replaceAll("\"", ""));

                        List<CardSet> allPrintings = convertedSets.stream()
                                .filter(set -> Arrays.asList(atomicCard.getPrintings())
                                        .contains(set.getCode()))
                                .toList();

                        cc.setWasReprinted(allPrintings.size() > 1);
                        CardSet firstPrinting = allPrintings.stream()
                                .min(Comparator.comparing(CardSet::getReleaseDate))
                                .orElse(null);

                        if (firstPrinting != null) {
                            cc.setFirstPrintingSetName(firstPrinting.getName());
                            cc.setFirstPrintingSetCode(firstPrinting.getCode());
                            cc.setFirstPrintingDate(firstPrinting.getReleaseDate());
                        }


                        Arrays.stream(atomicCard.getColorIdentity()).forEach(color -> {
                            if (color.equals("W"))
                                cc.setWhiteIdentity(true);
                            else if (color.equals("B"))
                                cc.setBlackIdentity(true);
                            else if (color.equals("U"))
                                cc.setBlueIdentity(true);
                            else if (color.equals("R"))
                                cc.setRedIdentity(true);
                            else if (color.equals("G"))
                                cc.setGreenIdentity(true);
                        });

                        cc.setNumColors(atomicCard.getColorIdentity().length);

                        cc.setEdhrecRank(atomicCard.getEdhrecRank());
                        cc.setReserved(atomicCard.isReserved());
                        cc.setManaValue(atomicCard.getManaValue());
                        try {
                            cc.setPower(Integer.parseInt(atomicCard.getPower()));
                        } catch (NumberFormatException nfe) {
                            cc.setPower(-1);
                        }
                        try {
                            cc.setToughness(Integer.parseInt(atomicCard.getToughness()));
                        } catch (NumberFormatException nfe) {
                            cc.setToughness(-1);
                        }
                    }
                });

            });
        });

        File fileOut = new File("mtg.csv");
        PrintWriter writer = new PrintWriter(fileOut);
        writer.println(ConvertedCard.getFileHeader());
        for (ConvertedCard cc : convertedCards)
            writer.println(cc.toString());
        writer.close();
    }
}
