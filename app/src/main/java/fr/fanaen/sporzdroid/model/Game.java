package fr.fanaen.sporzdroid.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Fanaen on 28/12/2015.
 */
public class Game extends SugarRecord {

    // -- Attributes --
    String name;

    // -- Constructors --
    public Game() {}

    public Game(String name) {
        this.name = name;
    }

    // -- Methods --
    List<Participation> getParticipations() {
        return find(Participation.class, "game = ?", String.valueOf(getId()));
    }
}
