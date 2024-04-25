package org.neo4j.repository;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.neo4j.entities.Player;
import org.neo4j.entities.Team;
import org.neo4j.utils.PrintUtils;

import java.util.Objects;
import java.util.UUID;

public class Repository {

    private final Driver driver;

    public Repository(Driver driver) {
        this.driver = driver;
    }

    public void getNodesByMark(String label) {
        String query = Objects.equals(label, "") ? "MATCH (n) RETURN n" : String.format("MATCH (n:%s) RETURN n", label);
        try (Session session = driver.session()) {
            Result result = session.run(query);
            PrintUtils.print(result);
        }
    }

    public void createTeam(Team team) {
        try (Session session = driver.session()) {
            UUID uuid = UUID.randomUUID();
            session.run("CREATE (n:Team) SET n.id = $id, n.name = $name, n.count = $count",
                    Values.parameters("id", uuid.toString(), "name", team.getName(), "count", team.getCount()));
        }
    }

    public void createPlayer(Player player) {
        try (Session session = driver.session()) {
            UUID uuid = UUID.randomUUID();
            session.run("CREATE (n:Player) SET n.id = $id, n.firstName = $firstName, n.lastName = $lastName",
                    Values.parameters("id", uuid.toString(), "firstName", player.getFirstName(), "lastName", player.getLastName()));
        }
    }

    public void deleteNode(String label, String id) {
        try (Session session = driver.session()) {
            session.run(String.format("MATCH (n:%s) WHERE n.id = $id DETACH DELETE n", label),
                    Values.parameters("id", id));
        }
    }

    public void updateTeam(Team team) {
        try (Session session = driver.session()) {
            session.run("MATCH (n:Team) WHERE n.id = $id SET n.name = $name, n.count = $count",
                    Values.parameters("id", team.getUuid(), "name", team.getName(), "count", team.getCount()));
        }
    }

    public void addPlayerToTeam(String teamId, String playerId) {
        try (Session session = driver.session()) {
            session.run("MATCH (n:Team), (p:Player) WHERE n.id = $teamId AND p.id = $playerId MERGE (n)-[:CONTAINS]->(p)",
                    Values.parameters("teamId", teamId,
                    "playerId", playerId));
        }
    }

    public void getTeamsPlayers(String teamName) {
        try (Session session = driver.session()) {
            Result result = session.run("MATCH (t:Team)-[:CONTAINS]->(n:Player) WHERE t.name = $name RETURN n", Values.parameters("name", teamName) );
            PrintUtils.print(result);
        }
    }
}