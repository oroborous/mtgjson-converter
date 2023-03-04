package com.javapuppy.mtgjson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
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

        List<ConvertedCard2> convertedCards = new ArrayList<>();

        for (String name : atomicCards.keySet()) {
            AtomicCard[] atomicCardList = atomicCards.get(name);

            for (AtomicCard atomicCard : atomicCardList) {
                ConvertedCard2 cc = new ConvertedCard2();

                if (atomicCard.getAsciiName() == null ||
                        atomicCard.getAsciiName().equals("null")) {
                    cc.setTitle(name);
                } else {
                    cc.setTitle(atomicCard.getAsciiName());
                }

                cc.setTitle(cc.getTitle().replaceAll("\"", ""));

                if (atomicCard.getTypes().length == 0) {
                    cc.setType("No Type");
                } else if (atomicCard.getTypes().length > 1) {
                    cc.setType("Multi-Type");
                } else {
                    String type = atomicCard.getTypes()[0];
                    if (type.equalsIgnoreCase("sorcery")) {
                        cc.setType("Sorcery");
                    } else if (type.equalsIgnoreCase("enchantment")) {
                        cc.setType("Enchantment");
                    } else if (type.equalsIgnoreCase("land")) {
                        cc.setType("Land");
                    } else if (type.equalsIgnoreCase("instant")) {
                        cc.setType("Instant");
                    } else if (type.equalsIgnoreCase("creature")) {
                        cc.setType("Creature");
                    } else if (type.equalsIgnoreCase("planeswalker")) {
                        cc.setType("Planeswalker");
                    } else if (type.equalsIgnoreCase("artifact")) {
                        cc.setType("Artifact");
//                    } else if (type.equalsIgnoreCase("scheme")) {
//                        cc.setType("Scheme");
//                    } else if (type.equalsIgnoreCase("vanguard")) {
//                        cc.setType("Vanguard");
//                    } else if (type.equalsIgnoreCase("plane")) {
//                        cc.setType("Plane");
//                    } else if (type.equalsIgnoreCase("conspiracy")) {
//                        cc.setType("Conspiracy");
//                    }else if (type.equalsIgnoreCase("hero")) {
//                        cc.setType("Hero");
                    }else {
                        System.out.printf("Skipping: Unrecognized type %s for %s%n", type, cc.getTitle());
                        continue;
                    }
                }

                List<CardSet> allPrintings = convertedSets.stream()
                        .filter(set -> Arrays.asList(atomicCard.getPrintings())
                                .contains(set.getCode()))
                        .collect(Collectors.toList());

                boolean isOnlineOnly = allPrintings.stream().allMatch(CardSet::isOnlineOnly);
                cc.setOnlineOnly(isOnlineOnly);

                cc.setWasReprinted(allPrintings.size() > 1);

                CardSet firstPrinting = allPrintings.stream()
                        .filter(set -> set.getReleaseDate() != null)
                        .min(Comparator.comparing(CardSet::getReleaseDate))
                        .orElse(null);

                if (firstPrinting != null) {
//                    // TODO: If first printing is Magic Online, then try to pick second printing instead
                    if (firstPrinting.getName().contains("Magic Online Promos")) {
                        System.out.println("WTH");
                        // try to find the second printing
                        allPrintings.remove(firstPrinting);
                        firstPrinting = allPrintings.stream()
                                .filter(set -> set.getReleaseDate() != null)
                                .min(Comparator.comparing(CardSet::getReleaseDate))
                                .orElse(firstPrinting);
                    }
                    cc.setFirstPrintingSetName(firstPrinting.getName());
                    cc.setFirstPrintingSetCode(firstPrinting.getCode());
                    cc.setFirstPrintingDate(firstPrinting.getReleaseDate());
                } else {
                    System.out.printf("Skipping: No release date for %s%n", cc.getTitle());
                    continue;
                }

                if (atomicCard.getColorIdentity().length == 0) {
                    cc.setColor("No Color");
                } else if (atomicCard.getColorIdentity().length > 1) {
                    cc.setColor("Multi-Color");
                } else {
                    String color = atomicCard.getColorIdentity()[0];
                    if (color.equals("W")) {
                        cc.setColor("White");
                    } else if (color.equals("B")) {
                        cc.setColor("Black");
                    } else if (color.equals("U")) {
                        cc.setColor("Blue");
                    } else if (color.equals("R")) {
                        cc.setColor("Red");
                    } else if (color.equals("G")) {
                        cc.setColor("Green");
                    }
                }


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

                convertedCards.add(cc);
            }
        }

        // Exclude sets with < 100 cards (promos, etc.)
        for(CardSet set : convertedSets) {
            String name = set.getName();

            long cardsInSet = convertedCards.stream()
                    .filter(card -> card.getFirstPrintingSetName().equals(name))
                    .count();
            System.out.printf("Only %d cards in set %s%n", cardsInSet, name);
            if (cardsInSet < 100) {
                convertedCards = convertedCards.stream()
                        .filter(card -> !card.getFirstPrintingSetName().equals(name))
                        .collect(Collectors.toList());
                System.out.printf("Total cards remaining: %d%n", convertedCards.size());
            }
        }


        File fileOut = new File("mtg2.csv");
        PrintWriter writer = new PrintWriter(fileOut);
        writer.println(ConvertedCard2.getFileHeader());
        for (ConvertedCard2 cc : convertedCards)
            writer.println(cc.toString());
        writer.flush();
        writer.close();
    }
}
