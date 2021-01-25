package com.example.fichefrise.data.repository;

import com.example.fichefrise.data.api.ThemeDisplayService;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.repository.remote.ThemeDisplayRemoteDataSource;

import java.util.List;

import io.reactivex.Single;

public class ThemeDisplayDataRepository implements ThemeDisplayService {

    private ThemeDisplayRemoteDataSource themeDisplayRemoteDataSource;

    public ThemeDisplayDataRepository(ThemeDisplayRemoteDataSource themeDisplayRemoteDataSource){
        this.themeDisplayRemoteDataSource = themeDisplayRemoteDataSource;
    }

    @Override
    public Single<List<Theme>> getAllThemes() {
        return this.themeDisplayRemoteDataSource.getAllThemes();
    }

    @Override
    public Single<Theme> getThemeById(int themeId) {
        return this.themeDisplayRemoteDataSource.getThemeById(themeId);
    }
}
