package com.example.fichefrise.data.repository;


import android.util.Log;

import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.NewFicheRequest;
import com.example.fichefrise.data.api.model.Theme;
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

    @Override
    public Single<Fiche> createNewFiche(NewFicheRequest request) {
        Log.i("DATA REPOSITORY", "On est ici");
        return this.ficheDisplayRemoteDataSource.createNewFiche(request);
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
