package com.example.fichefrise.presentation.display.fiche.mapper;

import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheViewItem;

import java.util.ArrayList;
import java.util.List;

public class FicheToViewModelMapper {

    public FicheViewItem map(Fiche fiche){
        FicheViewItem ficheViewItem = new FicheViewItem();
        ficheViewItem.setFicheId((fiche.getFicheId()));
        ficheViewItem.setNomFiche(fiche.getNomFiche());
        return ficheViewItem;
    }

    public List<FicheViewItem> map(List<Fiche> listFiches){
        List<FicheViewItem> listFichesViewItem = new ArrayList<>();
        if(listFiches != null) {
            for (Fiche f : listFiches) {
                listFichesViewItem.add(map(f));
            }
        }
        return listFichesViewItem;
    }
}
