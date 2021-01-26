package com.example.fichefrise.data.repository.remote;

import com.example.fichefrise.data.api.FriseDisplayService;
import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Frise;

import java.util.List;

import io.reactivex.Single;

public class FriseDisplayRemoteDataSource {

    private FriseDisplayService friseDisplayService;

    public FriseDisplayRemoteDataSource(FriseDisplayService friseDisplayService){
        this.friseDisplayService = friseDisplayService;
    }

    public Single<List<Frise>> getAllFrises(){
        return this.friseDisplayService.getAllFrises();
    }

    public Single<Frise> getFriseById(int friseId){
        return this.friseDisplayService.getFriseById(friseId);
    }

    public Single<List<Evenement>> getAllEvenements(int friseId){
        return this.friseDisplayService.getAllEvenements(friseId);
    }

    public Single<Evenement> getEvenementById(int friseId, int evenementId){
        return this.friseDisplayService.getEvenementById(friseId, evenementId);
    }
}
