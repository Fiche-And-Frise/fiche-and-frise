package com.example.fichefrise.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.fichefrise.data.repository.FicheDisplayRepository;

public class ViewModelFactory implements ViewModelProvider.Factory{

    private FicheDisplayRepository ficheDisplayRepository;

    public ViewModelFactory(FicheDisplayRepository ficheDisplayRepository){
        this.ficheDisplayRepository = ficheDisplayRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FicheViewModel.class)) {
            return (T) new FicheViewModel(ficheDisplayRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");    }
}
