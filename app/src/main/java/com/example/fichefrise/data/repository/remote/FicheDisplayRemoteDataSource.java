package com.example.fichefrise.data.repository.remote;

import android.util.Log;

import com.example.fichefrise.data.api.FicheDisplayService;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.NewFicheRequest;
import com.example.fichefrise.data.api.model.Theme;

import java.util.List;

import io.reactivex.Single;

public class FicheDisplayRemoteDataSource {
    private FicheDisplayService ficheDisplayService;

    public FicheDisplayRemoteDataSource(FicheDisplayService ficheDisplayService){
        this.ficheDisplayService = ficheDisplayService;
    }

    public Single<List<Fiche>> getAllFiches(){
        return this.ficheDisplayService.getAllFiches();
    }

    public Single<Fiche> getFicheById(int ficheId){
        return this.ficheDisplayService.getFicheById(ficheId);
    }

    public Single<Fiche> createNewFiche(NewFicheRequest request){
        Log.i("REMOTE DATA SOURCE", "On est ici");
        return this.ficheDisplayService.createNewFiche(request);
    }

}
