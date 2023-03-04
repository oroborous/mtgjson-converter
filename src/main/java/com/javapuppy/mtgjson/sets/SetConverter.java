package com.javapuppy.mtgjson.sets;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

    public void addPrices(File file) throws IOException {
        Map<String, PriceHistory> priceMap = mapper.readValue(file, PriceFile.class).getData();

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
                        cardMap.get(uuid).setAvgPrice(avgPrice);
                    } else {
                        System.out.printf("Zero price. Removing %s%n", uuid);
                        cardsToRemove.add(uuid);
                    }
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
