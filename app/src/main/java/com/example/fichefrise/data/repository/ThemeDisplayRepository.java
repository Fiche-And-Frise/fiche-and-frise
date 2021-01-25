package com.example.fichefrise.data.repository;

import com.example.fichefrise.data.api.model.Theme;

import java.util.List;

import io.reactivex.Single;

public interface ThemeDisplayRepository {
    Single<List<Theme>> getAllThemes();

    Single<Theme> getThemeById(int themeId);

}
