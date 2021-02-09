package com.example.fichefrise.data.api.model;

public class NewEvenementRequest {
    private Frise frise;
    private int theme;
    private Evenement evenement;
    private int index;

    public NewEvenementRequest(Frise frise, int theme, Evenement evenement, int index) {
        this.frise = frise;
        this.theme = theme;
        this.evenement = evenement;
        this.index = index;
    }

    public Frise getFrise() {
        return frise;
    }

    public void setFrise(Frise frise) {
        this.frise = frise;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
