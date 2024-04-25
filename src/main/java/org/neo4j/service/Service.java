package org.neo4j;

import org.neo4j.driver.Driver;
import org.cassandra.Team;


public class TeamService4 {
    private final Neo4jRepository repository;

    public TeamService4(Driver driver) {
        repository = new Neo4jRepository(driver);
    }

    public void createTeam(String label, Team team) {
        repository.createTeam(label, team);
    }

    public void updateTeam(String label, Team team) {
        repository.updateTeam(label, team);
    }

    public void deleteNode(String label, int id) {
        repository.deleteNode(label, id);
    }

    public void getNodesByMark(String label) {
        repository.getNodesByMark(label);
    }

    public void addPlayerToTeam(int teamId, Player player) {
        repository.addPlayerToTeam(teamId, player);
    }

    public void getTeamsPlayers(String teamName) {
        repository.getTeamsPlayers(teamName);
    }
}
