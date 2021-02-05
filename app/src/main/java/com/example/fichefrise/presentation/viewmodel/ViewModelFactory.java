package com.example.fichefrise.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.data.repository.FriseDisplayRepository;
import com.example.fichefrise.data.repository.ThemeDisplayRepository;

public class ViewModelFactory implements ViewModelProvider.Factory{

    private FicheDisplayRepository ficheDisplayRepository;
    private ThemeDisplayRepository themeDisplayRepository;
    private FriseDisplayRepository friseDisplayRepository;

    public ViewModelFactory(FicheDisplayRepository ficheDisplayRepository,
                            ThemeDisplayRepository themeDisplayRepository,
                            FriseDisplayRepository friseDisplayRepository){
        this.ficheDisplayRepository = ficheDisplayRepository;
        this.themeDisplayRepository = themeDisplayRepository;
        this.friseDisplayRepository = friseDisplayRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FicheViewModel.class)) {
            return (T) new FicheViewModel(ficheDisplayRepository, themeDisplayRepository);
        }
        if (modelClass.isAssignableFrom(FriseViewModel.class)) {
            return (T) new FriseViewModel(friseDisplayRepository, themeDisplayRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");    }
}
