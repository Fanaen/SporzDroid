package fr.fanaen.sporzdroid.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Fanaen on 28/12/2015.
 */
public class Person extends SugarRecord {

    // -- Attributes --
    String name;

    // -- Constructors --
    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    // -- Methods --
    List<Participation> getParticipations() {
        return find(Participation.class, "person = ?", String.valueOf(getId()));
    }
}
