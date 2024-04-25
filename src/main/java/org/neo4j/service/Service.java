package org.neo4j.service;

import org.neo4j.driver.Driver;
import org.neo4j.entities.Player;
import org.neo4j.entities.Team;
import org.neo4j.repository.Repository;


public class Service {
    private final Repository repository;

    public Service(Driver driver) {
        repository = new Repository(driver);
    }

    public void createTeam(Team team) {
        repository.createTeam(team);
    }

    public void createPlayer(Player player) {
        repository.createPlayer(player);
    }

    public void updateTeam(Team team) {
        repository.updateTeam(team);
    }

    public void deleteNode(String label, String id) {
        repository.deleteNode(label, id);
    }

    public void getNodesByMark(String label) {
        repository.getNodesByMark(label);
    }

    public void addPlayerToTeam(String teamId, String playerId) {
        repository.addPlayerToTeam(teamId, playerId);
    }

    public void getTeamsPlayers(String teamName) {
        repository.getTeamsPlayers(teamName);
    }
}
