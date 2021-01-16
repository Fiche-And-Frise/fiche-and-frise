package com.example.fichefrise.data.repository;


import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.repository.remote.FicheDisplayRemoteDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class FicheDisplayDataRepository implements FicheDisplayRepository {

    private FicheDisplayRemoteDataSource ficheDisplayRemoteDataSource;

    public FicheDisplayDataRepository(FicheDisplayRemoteDataSource ficheDisplayRemoteDataSource){
        this.ficheDisplayRemoteDataSource = ficheDisplayRemoteDataSource;
    }

    @Override
    public Single<List<Fiche>> getAllFiches() {
        return this.ficheDisplayRemoteDataSource.getAllFiches();
    }

    @Override
    public Single<Fiche> getFicheById(int ficheId) {
        return this.ficheDisplayRemoteDataSource.getFicheById(ficheId);
    }

    //A compléter ultérieurement
    @Override
    public Completable addFiche(int id) {
        return null;
    }

    @Override
    public Completable updateFiche(int id) {
        return null;
    }

    @Override
    public Completable removeFiche(int id) {
        return null;
    }
}
