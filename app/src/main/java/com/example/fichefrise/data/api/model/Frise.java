package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Frise {
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
}
