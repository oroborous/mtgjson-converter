package com.javapuppy.mtgjson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javapuppy.mtgjson.entity.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetConverter {
    private final Map<String, Card> cardMap = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public SetConverter() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    public void convert(File file) throws IOException {
        CardSet cardSetObj = mapper.readValue(file, SetFile.class).getData();

        for (Card card : cardSetObj.getCards()) {
            cardMap.put(card.getUuid(), card);
        }

        System.out.printf("Total cards: %d%n", cardMap.size());
    }

    public void addPrices(File... files) throws IOException {
        Map<String, PriceHistory> priceMap = new HashMap<>();

        for (File file : files) {
            Map<String, PriceHistory> fileMap = mapper.readValue(file, PriceFile.class).getData();
            for (String uuid : fileMap.keySet()) {
                // Do we already have a price history for this card, and do we have
                // paper card prices in this new entry?
                if (priceMap.containsKey(uuid) && fileMap.get(uuid).getPaper() != null) {
                    Map<String, Retailer> existingPaper = priceMap.get(uuid).getPaper();
                    Map<String, Retailer> newPaper = fileMap.get(uuid).getPaper();
                    for (String retailerName : newPaper.keySet()) {
                        // Do we already have this retailer's paper prices for this
                        // card and is this retailer using US dollars?
                        if (existingPaper.containsKey(retailerName) &&
                                newPaper.get(retailerName).getCurrency().equals("USD")) {
                            Retailer existingRetailer = existingPaper.get(retailerName);
                            Retailer newRetailer = newPaper.get(retailerName);

                            // Does this retailer have retail prices for the
                            // "normal" (non-foil) version of this card?
                            if (existingRetailer.getRetail() != null
                                    && newRetailer.getRetail() != null
                                    && existingRetailer.getRetail().getNormal() != null
                                    && newRetailer.getRetail().getNormal() != null) {
                                // Merge price history
                                existingRetailer.getRetail().getNormal().putAll(newRetailer.getRetail().getNormal());
                            } else if (newRetailer.getRetail() != null
                                    && newRetailer.getRetail().getNormal() != null) {
                                existingRetailer.setRetail(newRetailer.getRetail());
                            }

                            // Does this retailer have buylist prices for this card?
                            if (existingRetailer.getBuylist() != null
                                    && newRetailer.getBuylist() != null
                                    && existingRetailer.getBuylist().getNormal() != null
                                    && newRetailer.getBuylist().getNormal() != null) {
                                // Merge price history
                                existingRetailer.getBuylist().getNormal().putAll(newRetailer.getBuylist().getNormal());
                            } else if (newRetailer.getBuylist() != null
                                    && newRetailer.getBuylist().getNormal() != null) {
                                existingRetailer.setBuylist(newRetailer.getBuylist());
                            }
                        } else if (newPaper.get(retailerName).getCurrency().equals("USD")) {
                            existingPaper.put(retailerName, newPaper.get(retailerName));
                        }
                    }
                } else if (fileMap.get(uuid).getPaper() != null) {
                    priceMap.put(uuid, fileMap.get(uuid));
                }
            }

            System.out.println("Cards in price map: " + priceMap.size());
        }

        List<String> cardsToRemove = new ArrayList<>();

        for (String uuid : cardMap.keySet()) {
            if (priceMap.containsKey(uuid)) {
                PriceHistory history = priceMap.get(uuid);

                if (history.getPaper() == null) {
                    System.out.printf("No paper. Removing %s%n", uuid);
                    cardsToRemove.add(uuid);
                } else {
                    double avgPrice = history.getPaper().values().stream()
                            .filter(ret -> ret.getCurrency().equals("USD"))
                            .filter(ret -> ret.getRetail() != null && ret.getRetail().getNormal() != null)
                            .flatMapToDouble(ret -> ret.getRetail().getNormal().values()
                                    .stream().mapToDouble(Double::doubleValue))
                            .average()
                            .orElse(0);
                    if (avgPrice > 0) {
                        cardMap.get(uuid).setAvgRetailPrice(avgPrice);
                    } else {
                        System.out.printf("Zero retail price. Removing %s%n", uuid);
                        cardsToRemove.add(uuid);
                    }

                    avgPrice = history.getPaper().values().stream()
                            .filter(ret -> ret.getCurrency().equals("USD"))
                            .filter(ret -> ret.getBuylist() != null && ret.getBuylist().getNormal() != null)
                            .flatMapToDouble(ret -> ret.getBuylist().getNormal().values()
                                    .stream().mapToDouble(Double::doubleValue))
                            .average()
                            .orElse(0);
                    cardMap.get(uuid).setAvgBuylistPrice(avgPrice);
                }
            } else {
                System.out.printf("No prices. Removing %s%n", uuid);
                cardsToRemove.add(uuid);
            }
        }

        System.out.printf("Removing %d cards...%n", cardsToRemove.size());
        for (String cardToRemove : cardsToRemove) {
            cardMap.remove(cardToRemove);
        }
    }

    public void printCsv(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(Card.getFileHeader());
            for (Card card : cardMap.values())
                writer.println(card.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
