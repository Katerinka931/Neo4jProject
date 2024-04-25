package org.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.Node;
import org.cassandra.Team;

import java.util.Objects;

public class Neo4jRepository {

    private final Driver driver;

    public Neo4jRepository(Driver driver) {
        this.driver = driver;
    }

    public void getNodesByMark(String label) {
        String query = Objects.equals(label, "") ? "MATCH (n) RETURN n" : String.format("MATCH (n:%s) RETURN n", label);

        try (Session session = driver.session()) {
            Result result = session.run(query);
            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("n").asNode();
                System.out.println(node.asMap());
            }
        }
    }

    public void createTeam(String label, Team team) {
        try (Session session = driver.session()) {
            session.run(String.format("CREATE (n:%s) SET n.id = $id, n.name = $name, n.count = $count", label),
                    Values.parameters("id", team.getId(), "name", team.getName(), "count", team.getCount()));
        }
    }

    public void deleteNode(String label, int id) {
        try (Session session = driver.session()) {
            session.run(String.format("MATCH (n:%s) WHERE ID(n) = $id DETACH DELETE n", label),
                    Values.parameters("id", id));
        }
    }

    public void updateTeam(String label, Team team) {
        try (Session session = driver.session()) {
            session.run(String.format("MATCH (n:%s) WHERE ID(n) = $id SET n.name = $name, n.count = $count", label),
                    Values.parameters("name", team.getName(), "count", team.getCount()));
        }
    }

    //    todo тут две версии как писать ACTED_IN
    public void addPlayerToTeam(int teamId, Player player) {
        try (Session session = driver.session()) {
            session.run("MERGE (p:Player {firstName: $firstName, lastName: $lastName})-[:ACTED_IN]->(n:Team {id: $teamId}) " +
                    "RETURN p, m", Values.parameters("firstName", player.getFirstName(),
                    "lastName", player.getLastName(), "teamId", teamId));
        }
    }

    public void getTeamsPlayers(String teamName) {
        try (Session session = driver.session()) {
            Result result = session.run(String.format("MATCH (t:Team {name: %s}) <-[ACTED_IN]-(p:Player) RETURN p", teamName));
            while (result.hasNext()) {
                Record record = result.next();
                Node node = record.get("n").asNode();
                System.out.println(node.asMap());
            }
        }
    }
}