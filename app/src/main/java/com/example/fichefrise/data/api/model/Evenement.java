package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Evenement implements Serializable {

    @SerializedName("name")
    private String nomEvenement;

    @SerializedName("dateDebut")
    private String dateDebutEvenement;

    public Evenement(String nomEvenement, String dateDebutEvenement) {
        this.nomEvenement = nomEvenement;
        this.dateDebutEvenement = dateDebutEvenement;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public String getDateDebutEvenement() {
        return dateDebutEvenement;
    }

    public void setDateDebutEvenement(String dateDebutEvenement) {
        this.dateDebutEvenement = dateDebutEvenement;
    }
}
