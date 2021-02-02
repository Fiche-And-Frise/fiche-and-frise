package com.example.fichefrise.presentation.display.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.fichefrise.data.LoginDataSource;
import com.example.fichefrise.data.LoginRepository;
import com.example.fichefrise.data.di.FakeDependencyInjection;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(FakeDependencyInjection.getLoginRepository());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}