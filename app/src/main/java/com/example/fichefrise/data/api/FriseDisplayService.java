package com.example.fichefrise.data.api;


import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.NewEvenementRequest;
import com.example.fichefrise.data.api.model.NewFriseRequest;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FriseDisplayService {

    @GET("frises")
    Single<List<Frise>> getAllFrises();

    @GET("frises/{id}")
    Single<Frise> getFriseById(@Path("id") int friseId);

    @GET("frises/{id}/evenements")
    Single<List<Evenement>> getAllEvenements(@Path("id") int friseId);

    @GET("frises/{id}/evenements/{idEvent}")
    Single<Evenement> getEvenementById(@Path("id") int friseId, @Path("idEvent") int evenementId);

    @POST("frises/new")
    Single<Frise> createNewFrise(@Body NewFriseRequest request);

    @PUT("frises/update/evenement")
    Single<Frise> createNewEvenement(@Body NewEvenementRequest request);

    @POST("/frises/delete/{id}")
    Completable deleteFrise(@Path("id") int friseId);

    @POST("/frises/delete/evenement")
    Single<Frise> deleteEvenement(@Body NewFriseRequest request);
}
