package org.neo4j.utils;

import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.types.Node;

public class PrintUtils {
    public static void print(Result result) {
        if (!result.hasNext())
            System.out.println("Запрос не дал результатов");
        else
            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("n").asNode();
                System.out.println(node.asMap());
            }
    }
}
