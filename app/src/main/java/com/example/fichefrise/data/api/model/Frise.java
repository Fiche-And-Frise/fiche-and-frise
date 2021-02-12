package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Frise implements Serializable {
    @SerializedName("id")
    private int friseId;

    @SerializedName("name")
    private String nomFrise;

    @SerializedName("dateDebut")
    private int dateDebutFrise;

    @SerializedName("dateFin")
    private int dateFinFrise;

    @SerializedName("evenements")
    private List<Evenement> listEvenements;

    private int color;

    public Frise(String nomFrise, int dateDebutFrise, int dateFinFrise) {
        this.nomFrise = nomFrise;
        this.dateDebutFrise = dateDebutFrise;
        this.dateFinFrise = dateFinFrise;
        this.listEvenements = Collections.emptyList();
    }

    private int currentTheme;

    public int getFriseId() {
        return friseId;
    }

    public void setFriseId(int friseId) {
        this.friseId = friseId;
    }

    public String getNomFrise() {
        return nomFrise;
    }

    public void setNomFrise(String nomFrise) {
        this.nomFrise = nomFrise;
    }

    public int getDateDebutFrise() {
        return dateDebutFrise;
    }

    public void setDateDebutFrise(int dateDebutFrise) {
        this.dateDebutFrise = dateDebutFrise;
    }

    public int getDateFinFrise() {
        return dateFinFrise;
    }

    public void setDateFinFrise(int dateFinFrise) {
        this.dateFinFrise = dateFinFrise;
    }

    public List<Evenement> getListEvenements() {
        return listEvenements;
    }

    public void setListEvenements(List<Evenement> listEvenements) {
        this.listEvenements = listEvenements;
    }

    public int getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(int currentTheme) {
        this.currentTheme = currentTheme;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public static Comparator<Frise> comparator = (f1, f2) -> f1.getNomFrise().compareTo(f2.getNomFrise());


}
