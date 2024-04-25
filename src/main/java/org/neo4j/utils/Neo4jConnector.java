package org.neo4j;

import lombok.Getter;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class Neo4jConnector {
    @Getter
    private final Driver driver;
    private final String url = "bolt://localhost:7687";
    private final String username = "username";
    private final String password = "password";
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
