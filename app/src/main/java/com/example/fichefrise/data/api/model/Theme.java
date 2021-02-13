package com.example.fichefrise.data.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Theme implements Serializable {

    @SerializedName("id")
    private int themeId;

    @SerializedName("name")
    private String nomTheme;

    @SerializedName("color")
    private int color;

    @SerializedName("listFiches")
    private List<Fiche> listFiches;

    @SerializedName("listFrises")
    private List<Frise> listFrises;

    public Theme(String name, int color){
        this.themeId = 0;
        this.nomTheme = name;
        this.color = color;
        this.listFiches = new ArrayList<>();
        this.listFrises = new ArrayList<>();
    }

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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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

    public static Comparator<Theme> comparator = (t1, t2) -> t1.getNomTheme().compareTo(t2.getNomTheme());

}
