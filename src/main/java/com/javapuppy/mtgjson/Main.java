package com.javapuppy.mtgjson;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SetConverter converter = new SetConverter();

        File folder = new File("sets");
        File[] files = folder.listFiles();

        for (final File setFile : files) {
            converter.convert(setFile);
        }

        converter.addPrices(new File("AllPrices1.json"), new File("AllPrices2.json"));

        converter.loadSets(new File("SetList.json"));

        converter.printCsv(new File("mtg-w-prices.csv"));
    }
}
