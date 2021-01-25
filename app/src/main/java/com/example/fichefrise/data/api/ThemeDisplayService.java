package com.example.fichefrise.data.api;

import com.example.fichefrise.data.api.model.Theme;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ThemeDisplayService {
    @GET("themes")
    Single<List<Theme>> getAllThemes();

    @GET("themes/{id}")
    Single<Theme> getThemeById(@Path("id") int themeId);
}
