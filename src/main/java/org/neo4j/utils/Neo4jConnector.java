package org.neo4j.utils;

import lombok.Getter;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class Neo4jConnector {
    @Getter
    private final Driver driver;
    private final String url = "bolt://0.0.0.0:7687";
    private final String username = "neo4j";
    private final String password = "nixon-balsa-voice-agent-atomic-3065";
    private static Neo4jConnector INSTANCE;

    private Neo4jConnector(){
        driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
    }

    public static Neo4jConnector getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Neo4jConnector();
        return INSTANCE;
    }

    public void close() {
        driver.close();
    }
}
