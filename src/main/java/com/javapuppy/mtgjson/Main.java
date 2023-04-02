package com.javapuppy.mtgjson;

import com.javapuppy.mtgjson.entity.cardfile.Card;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        SetConverter converter = new SetConverter();

        File folder = new File("sets");
        File[] files = folder.listFiles();

        for (final File setFile : files) {
            converter.convert(setFile);
        }

        Map<String, Card> cards = converter.addPrices(new File("AllPrices1.json"), new File("AllPrices2.json"));

        converter.loadSets(new File("SetList.json"));

        converter.printCardCsv(new File("mtg-cards.csv"));

        converter.printPriceCsv(new File("mtg-prices.csv"));

        converter.printSetCsv(new File("mtg-sets.csv"));

        CollectionGenerator collectionGenerator = new CollectionGenerator();
        String[] users = {"stacy", "fry", "bean", "sara", "mark",
                "gigi", "eliot", "charlie", "roman", "maci"};
        for (String user : users) {
            collectionGenerator.generateCollection(new File(String.format("mtg-collection-%s.csv", user)), user + "@email.com", cards);
        }
    }
}
