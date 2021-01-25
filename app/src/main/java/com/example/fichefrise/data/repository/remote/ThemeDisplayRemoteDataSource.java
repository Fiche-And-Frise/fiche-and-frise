package com.example.fichefrise.data.repository.remote;

import com.example.fichefrise.data.api.ThemeDisplayService;
import com.example.fichefrise.data.api.model.Theme;

import java.util.List;

import io.reactivex.Single;

public class ThemeDisplayRemoteDataSource {
    private ThemeDisplayService themeDisplayService;

    public ThemeDisplayRemoteDataSource(ThemeDisplayService themeDisplayService){
        this.themeDisplayService = themeDisplayService;
    }

    public Single<List<Theme>> getAllThemes(){
        return this.themeDisplayService.getAllThemes();
    }

    public Single<Theme> getThemeById(int themeId){
        return this.themeDisplayService.getThemeById(themeId);
    }
}
