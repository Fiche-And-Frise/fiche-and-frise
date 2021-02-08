package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Evenement implements Serializable {

    @SerializedName("id")
    private int evenementId;

    @SerializedName("name")
    private String nomEvenement;

    @SerializedName("dateDebut")
    private int dateDebutEvenement;

    public Evenement(String nomEvenement, int dateDebutEvenement) {
        this.nomEvenement = nomEvenement;
        this.dateDebutEvenement = dateDebutEvenement;
    }

    public int getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public int getDateDebutEvenement() {
        return dateDebutEvenement;
    }

    public void setDateDebutEvenement(int dateDebutEvenement) {
        this.dateDebutEvenement = dateDebutEvenement;
    }
}
