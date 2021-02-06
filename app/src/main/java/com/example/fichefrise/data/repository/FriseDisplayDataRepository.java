package com.example.fichefrise.data.repository;

import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.NewFriseRequest;
import com.example.fichefrise.data.repository.remote.FriseDisplayRemoteDataSource;

import java.util.List;

import io.reactivex.Single;

public class FriseDisplayDataRepository implements FriseDisplayRepository{

    private FriseDisplayRemoteDataSource friseDisplayRemoteDataSource;

    public FriseDisplayDataRepository(FriseDisplayRemoteDataSource friseDisplayRemoteDataSource){
        this.friseDisplayRemoteDataSource = friseDisplayRemoteDataSource;
    }

    @Override
    public Single<List<Frise>> getAllFrises() {
        return this.friseDisplayRemoteDataSource.getAllFrises();
    }

    @Override
    public Single<Frise> getFriseById(int friseId) {
        return this.friseDisplayRemoteDataSource.getFriseById(friseId);
    }

    @Override
    public Single<List<Evenement>> getAllEvenements(int friseId) {
        return this.friseDisplayRemoteDataSource.getAllEvenements(friseId);
    }

    @Override
    public Single<Evenement> getEvenementById(int friseId, int evenementId) {
        return this.friseDisplayRemoteDataSource.getEvenementById(friseId, evenementId);
    }

    @Override
    public Single<Frise> createNewFrise(NewFriseRequest request) {
        return this.friseDisplayRemoteDataSource.createNewFrise(request);
    }
}
