package com.example.fichefrise.data.api.model;

public class NewEvenementRequest {
    private Frise frise;
    private Theme theme;
    private Evenement evenement;

    public NewEvenementRequest(Frise frise, Theme theme, Evenement evenement) {
        this.frise = frise;
        this.theme = theme;
        this.evenement = evenement;
    }

    public Frise getFrise() {
        return frise;
    }

    public void setFrise(Frise frise) {
        this.frise = frise;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }
}
