package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Evenement implements Serializable {

    @SerializedName("name")
    private String nomEvenement;

    @SerializedName("dateDebut")
    private String dateDebutEvenement;

    @SerializedName("ficheId")
    private int ficheId;
    @SerializedName("themeId")
    private int themeId;

    private int color;

    public Evenement(String nomEvenement, String dateDebutEvenement) {
        this.nomEvenement = nomEvenement;
        this.dateDebutEvenement = dateDebutEvenement;
        this.ficheId = -1;
        this.themeId = -1;
        this.color = 0;
    }

    public Evenement(String nomEvenement, String dateDebutEvenement, int ficheId, int themeId) {
        this.nomEvenement = nomEvenement;
        this.dateDebutEvenement = dateDebutEvenement;
        this.ficheId = ficheId;
        this.themeId = themeId;
        this.color = 0;
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

    public int getFicheId() {
        return ficheId;
    }

    public void setFicheId(int ficheId) {
        this.ficheId = ficheId;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
