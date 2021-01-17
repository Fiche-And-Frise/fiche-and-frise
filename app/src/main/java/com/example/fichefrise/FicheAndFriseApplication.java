package com.example.fichefrise;

import android.app.Application;

import com.example.fichefrise.data.di.FakeDependencyInjection;

public class FicheAndFriseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FakeDependencyInjection.setContext(this);
    }
}
