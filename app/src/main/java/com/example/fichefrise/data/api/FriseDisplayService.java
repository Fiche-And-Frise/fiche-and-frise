package com.example.fichefrise.data.api;


import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Frise;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FriseDisplayService {

    @GET("frises")
    Single<List<Frise>> getAllFrises();

    @GET("frises/{id}")
    Single<Frise> getFriseById(@Path("id") int friseId);

    @GET("frises/{id}/evenements")
    Single<List<Evenement>> getAllEvenements();

    @GET("frises/{id}/evenements/{idEvent}")
    Single<Frise> getEvenementById(@Path("id") int friseId, @Path("idEvent") int evenementId);
}
