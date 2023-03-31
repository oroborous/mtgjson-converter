package com.javapuppy.mtgjson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javapuppy.mtgjson.entity.cardfile.Card;
import com.javapuppy.mtgjson.entity.cardfile.CardSet;
import com.javapuppy.mtgjson.entity.cardfile.CardSetFile;
import com.javapuppy.mtgjson.entity.pricefile.PriceFile;
import com.javapuppy.mtgjson.entity.pricefile.PriceHistory;
import com.javapuppy.mtgjson.entity.pricefile.Retailer;
import com.javapuppy.mtgjson.entity.setfile.SetDetails;
import com.javapuppy.mtgjson.entity.setfile.SetFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class SetConverter {
    private Map<String, Card> cardMap = new HashMap<>();
    private final Map<String, PriceHistory> priceMap = new HashMap<>();
    public static final Map<String, SetDetails> setDetailsMap = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public SetConverter() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    public void loadSets(File file) throws IOException {
        List<SetDetails> sets = mapper.readValue(file, SetFile.class).getData();
        for (SetDetails set : sets) {
            setDetailsMap.put(set.getCode(), set);
        }
    }

    public void convert(File file) throws IOException {
        CardSet cardSetObj = mapper.readValue(file, CardSetFile.class).getData();

        for (Card card : cardSetObj.getCards()) {
            cardMap.put(card.getUuid(), card);
        }

        System.out.printf("Total cards: %d%n", cardMap.size());
    }

    public void addPrices(File... files) throws IOException {
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
                    cardMap.get(uuid).setAvgRetailPrice(avgPrice);

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
            priceMap.remove(cardToRemove);
        }
        System.out.printf("%d cards remaining...%n", cardMap.size());
        System.out.printf("%d prices remaining...%n", priceMap.size());

        cardsToRemove.clear();

        // Remove prices that don't have cards in the card map
        for (String uuid : priceMap.keySet()) {
            if (!cardMap.containsKey(uuid)) {
                cardsToRemove.add(uuid);
            }
        }
        // Remove cards that don't have prices
        for (String uuid : cardMap.keySet()) {
            if (!priceMap.containsKey(uuid)) {
                cardsToRemove.add(uuid);
            }
        }

        System.out.printf("Removing %d cards...%n", cardsToRemove.size());
        for (String cardToRemove : cardsToRemove) {
            cardMap.remove(cardToRemove);
            priceMap.remove(cardToRemove);
        }
        System.out.printf("%d cards remaining...%n", cardMap.size());
        System.out.printf("%d prices remaining...%n", priceMap.size());
    }

    public void printPriceCsv(File file) {
        Set<String> dateSet = priceMap.values().stream()
                .flatMap(priceHistory -> priceHistory.getPaper().values().stream())
                .filter(retailer -> retailer.getRetail() != null && retailer.getRetail().getNormal() != null)
                .flatMap(retailer -> retailer.getRetail().getNormal().keySet().stream())
                .collect(Collectors.toSet());
        priceMap.values().stream()
                .flatMap(priceHistory -> priceHistory.getPaper().values().stream())
                .filter(retailer -> retailer.getBuylist() != null && retailer.getBuylist().getNormal() != null)
                .flatMap(retailer -> retailer.getBuylist().getNormal().keySet().stream())
                .forEach(dateSet::add);
        List<String> dateList = dateSet.stream().sorted().toList();

        String headers = String.format("%s,%s,%s,%s",
                "uuid",
                "retailer",
                "priceType",
                String.join(",", dateList));
        String lineFormat = "%s,%s,%s,";

        long lineCount = 1; // for header

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(headers);
            for (Map.Entry<String, PriceHistory> priceHistoryEntry : priceMap.entrySet()) {
                String uuid = priceHistoryEntry.getKey();
                for (Map.Entry<String, Retailer> retailerEntry : priceHistoryEntry.getValue().getPaper().entrySet()) {
                    String retailer = retailerEntry.getKey();
                    if (retailerEntry.getValue().getRetail() != null
                            && retailerEntry.getValue().getRetail().getNormal() != null) {
                        Map<String, Double> retailPrices = retailerEntry.getValue().getRetail().getNormal();
                        String priceType = "retail";
                        double[] prices = new double[dateList.size()];
                        for (int i = 0; i < dateList.size(); i++) {
                            prices[i] = retailPrices.getOrDefault(dateList.get(i), 0.);
                        }
                        writer.print(String.format(lineFormat, uuid, retailer, priceType));
                        writer.println(Arrays.stream(prices).mapToObj(Double::toString).collect(Collectors.joining(",")));
                        lineCount++;
                    }

                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println(String.format("Columns: %d, Rows: %d, Cells: %d",
                3 + dateList.size(),
                lineCount,
                (3 + dateList.size()) * lineCount));
    }

    public void printCardCsv(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(Card.getFileHeader());
            for (Card card : cardMap.values())
                writer.println(card.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        cardMap = null;
    }
}
