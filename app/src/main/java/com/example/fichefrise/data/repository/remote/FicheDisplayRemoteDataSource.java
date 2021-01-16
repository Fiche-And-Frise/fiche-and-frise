package com.example.fichefrise.data.repository.remote;

import com.example.fichefrise.data.api.FicheDisplayService;
import com.example.fichefrise.data.api.model.Fiche;

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

}
