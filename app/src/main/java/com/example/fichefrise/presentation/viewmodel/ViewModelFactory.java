package com.example.fichefrise.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.data.repository.ThemeDisplayRepository;

public class ViewModelFactory implements ViewModelProvider.Factory{

    private FicheDisplayRepository ficheDisplayRepository;
    private ThemeDisplayRepository themeDisplayRepository;

    public ViewModelFactory(FicheDisplayRepository ficheDisplayRepository, ThemeDisplayRepository themeDisplayRepository){
        this.ficheDisplayRepository = ficheDisplayRepository;
        this.themeDisplayRepository = themeDisplayRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FicheViewModel.class)) {
            return (T) new FicheViewModel(ficheDisplayRepository, themeDisplayRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");    }
}
