package com.example.fichefrise.data.repository;

import com.example.fichefrise.data.api.model.Fiche;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FicheDisplayRepository {

    Single<List<Fiche>> getAllFiches();

    Single<Fiche> getFicheById(int ficheId);

    Completable addFiche(int id);

    Completable updateFiche(int id);

    Completable removeFiche(int id);

}
