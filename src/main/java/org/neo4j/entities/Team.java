package org.neo4j.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
@Setter
@AllArgsConstructor
public class Team {
    @Id
    private String uuid;
    private String name;
    private int count;

    public Team(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
