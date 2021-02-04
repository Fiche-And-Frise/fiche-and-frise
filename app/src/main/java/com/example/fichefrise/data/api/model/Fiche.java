package com.example.fichefrise.data.api.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fiche implements Serializable {

    @SerializedName("id")
    private int ficheId;

    @SerializedName("name")
    private String nomFiche;

    @SerializedName("recto")
    private String recto;

    @SerializedName("verso")
    private String verso;

    private int themeId;

    public Fiche(String nomFiche, String recto, String verso) {
        this.nomFiche = nomFiche;
        this.recto = recto;
        this.verso = verso;
    }

    public int getFicheId() {
        return ficheId;
    }

    public void setFicheId(int ficheId) {
        this.ficheId = ficheId;
    }

    public String getNomFiche() {
        return nomFiche;
    }

    public void setNomFiche(String nomFiche) {
        this.nomFiche = nomFiche;
    }

    public String getRecto() {
        return recto;
    }

    public void setRecto(String recto) {
        this.recto = recto;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    @NonNull
    @Override
    public String toString() {
        String response = "Id : " + this.getFicheId() +
                "\nNom : " + this.getNomFiche();
        return response;
    }
}
