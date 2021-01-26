package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Theme {

    @SerializedName("id")
    private int themeId;

    @SerializedName("name")
    private String nomTheme;

    @SerializedName("color")
    private String color;

    @SerializedName("listFiches")
    private List<Fiche> listFiches;

    @SerializedName("listFrises")
    private List<Frise> listFrises;

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getNomTheme() {
        return nomTheme;
    }

    public void setNomTheme(String nomTheme) {
        this.nomTheme = nomTheme;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Fiche> getListFiches() {
        return listFiches;
    }

    public void setListFiches(List<Fiche> listFiches) {
        this.listFiches = listFiches;
    }

    public List<Frise> getListFrises() {
        return listFrises;
    }

    public void setListFrises(List<Frise> listFrises) {
        this.listFrises = listFrises;
    }
}
