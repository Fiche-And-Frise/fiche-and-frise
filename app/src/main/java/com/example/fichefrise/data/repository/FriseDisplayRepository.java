package com.example.fichefrise.data.repository;

import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.NewFriseRequest;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FriseDisplayRepository {

    Single<List<Frise>> getAllFrises();

    Single<Frise> getFriseById(int friseId);

    Single<List<Evenement>> getAllEvenements(int friseId);

    Single<Evenement> getEvenementById(int friseId, int evenementId);

    Single<Frise> createNewFrise(NewFriseRequest request);
}
