package com.javapuppy.mtgjson;

import com.javapuppy.mtgjson.entity.cardfile.Card;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CollectionGenerator {
    public void generateCollection(File file, String userEmail, Map<String, Card> cards) {
        Random random = new Random();
        double targetPercent = random.nextDouble(0.04, 0.25);
        Set<String> collection = new HashSet<>();

        List<String> uuidList = cards.keySet().stream().toList();

        while (collection.size() < targetPercent * cards.size()) {
            int randomIndex = random.nextInt(cards.size());
            String randomUuid = uuidList.get(randomIndex);
            collection.add(randomUuid);
        }

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("userEmail,uuid,count");
            for (String uuid : collection) {
                int count = random.nextInt(1, 12);
                writer.println(String.format("%s,%s,%d", userEmail, uuid, count));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
