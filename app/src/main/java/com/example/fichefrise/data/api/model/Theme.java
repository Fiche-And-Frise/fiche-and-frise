package com.example.fichefrise.data.api.model;

import java.util.List;

public class Theme {

    private String name;
    private String color;
    private List<Fiche> fiches;
    //private List<Frise> frises;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Fiche> getFiches() {
        return fiches;
    }

    public void setFiches(List<Fiche> fiches) {
        this.fiches = fiches;
    }

    /*public List<Frise> getFrises() {
        return frises;
    }

    public void setFrises(List<Frise> frises) {
        this.frises = frises;
    }*/
}
