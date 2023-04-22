package com.javapuppy.mtgjson;

import com.javapuppy.mtgjson.entity.cardfile.Card;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        SetConverter converter = new SetConverter();

        File folder = new File("sets");
        File[] files = folder.listFiles();

        for (final File setFile : files) {
            converter.convert(setFile);
        }

        Map<String, Card> cards = converter.addPrices(new File("AllPrices1.json"),
                new File("AllPrices2.json"), new File("AllPrices3.json"));

        converter.loadSets(new File("SetList.json"));

        converter.printCardCsv(new File("mtg-cards.csv"));

        converter.printLongPriceCsv();



        converter.printSetCsv(new File("mtg-sets.csv"));

        CollectionGenerator collectionGenerator = new CollectionGenerator();
        String[] users = {"stacy", "fry", "bean", "sara", "mark",
                "gigi", "eliot", "charlie", "roman", "maci"};
        File collectionFile = new File("mtg-collections.csv");

        try (PrintWriter writer = new PrintWriter(collectionFile)) {
            writer.println("userEmail,uuid,count");
            for (String user : users) {
                collectionGenerator.generateCollection(writer, user + "@email.com", cards);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
