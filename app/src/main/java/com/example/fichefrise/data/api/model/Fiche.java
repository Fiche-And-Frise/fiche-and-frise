package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

public class Fiche {

    @SerializedName("id")
    private int ficheId;

    @SerializedName("name")
    private String nomFiche;

    @SerializedName("recto")
    private String recto;

    @SerializedName("verso")
    private String verso;

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
}
