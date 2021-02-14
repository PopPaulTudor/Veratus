package com.example.paul.myapp.model;

import java.util.UUID;

/**
 * Created by Paul on 17.03.2017.
 */

public class Event {

    UUID id;
    String type;
    String nume;

    public Event(UUID iid)
    {
        id=iid;
    }

    public Event(UUID id, String type, String nume) {
        this.id = id;
        this.type = type;
        this.nume = nume;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }






}
