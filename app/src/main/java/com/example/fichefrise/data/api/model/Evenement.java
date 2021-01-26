package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

public class Evenement {

    @SerializedName("id")
    private int evenementId;

    @SerializedName("name")
    private String nomEvenement;

    @SerializedName("dateDebut")
    private int dateDebutEvenement;

    @SerializedName("dateDebut")
    private int dateFinEvenement;

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

    public int getDateFinEvenement() {
        return dateFinEvenement;
    }

    public void setDateFinEvenement(int dateFinEvenement) {
        this.dateFinEvenement = dateFinEvenement;
    }
}
