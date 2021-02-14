package com.example.paul.myapp.model;

/**
 * Created by poppa on 04.06.2017.
 */

public class ColorModal {

    int colorAtribute;
    String name;

    public ColorModal(int colorAtribute, String name) {
        this.colorAtribute = colorAtribute;
        this.name = name;
    }

    public int getColorAtribute() {
        return colorAtribute;
    }

    public void setColorAtribute(int colorAtribute) {
        this.colorAtribute = colorAtribute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
