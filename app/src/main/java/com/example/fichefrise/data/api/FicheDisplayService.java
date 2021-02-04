package com.example.fichefrise.data.api;

import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.NewFicheRequest;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FicheDisplayService {

    @GET("fiches")
    Single<List<Fiche>> getAllFiches();

    @GET("fiches/{id}")
    Single<Fiche> getFicheById(@Path("id") int ficheId);

    @POST("fiches/new")
    Single<Fiche> createNewFiche(@Body NewFicheRequest request);

    @POST("fiches/delete/{id}")
    Completable deleteFiche(@Path("id") int id);
}
