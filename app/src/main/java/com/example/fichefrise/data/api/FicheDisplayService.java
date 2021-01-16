package com.example.fichefrise.data.api;

import com.example.fichefrise.data.api.model.Fiche;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FicheDisplayService {

    @GET("fiches")
    Single<List<Fiche>> getAllFiches();

    @GET("fiches/{id}")
    Single<Fiche> getFicheById(@Path("id") int ficheId);

}
