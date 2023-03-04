package com.javapuppy.mtgjson;

import com.javapuppy.mtgjson.sets.SetConverter;

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

        converter.addPrices(new File("AllPrices.json"));

        converter.printCsv(new File("mtg-w-prices.csv"));
    }
}
