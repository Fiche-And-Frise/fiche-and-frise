package com.example.fichefrise.data.di;

import android.content.Context;

import com.example.fichefrise.data.LoginDataSource;
import com.example.fichefrise.data.LoginRepository;
import com.example.fichefrise.data.api.FicheDisplayService;
import com.example.fichefrise.data.api.FriseDisplayService;
import com.example.fichefrise.data.api.LoginService;
import com.example.fichefrise.data.api.ThemeDisplayService;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.repository.FicheDisplayDataRepository;
import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.data.repository.FriseDisplayDataRepository;
import com.example.fichefrise.data.repository.FriseDisplayRepository;
import com.example.fichefrise.data.repository.ThemeDisplayDataRepository;
import com.example.fichefrise.data.repository.ThemeDisplayRepository;
import com.example.fichefrise.data.repository.remote.FicheDisplayRemoteDataSource;
import com.example.fichefrise.data.repository.remote.FriseDisplayRemoteDataSource;
import com.example.fichefrise.data.repository.remote.ThemeDisplayRemoteDataSource;
import com.example.fichefrise.presentation.viewmodel.ViewModelFactory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FakeDependencyInjection {

    private static Retrofit retrofit;
    private static Gson gson;
    private static Context applicationContext;

    private static ThemeDisplayRepository themeDisplayRepository;
    private static ThemeDisplayService themeDisplayService;
    private static FicheDisplayRepository ficheDisplayRepository;
    private static FicheDisplayService ficheDisplayService;
    private static FriseDisplayRepository friseDisplayRepository;
    private static FriseDisplayService friseDisplayService;
    private static LoginService loginService;
    private static LoginRepository loginRepository;
    private static ViewModelFactory viewModelFactory;

    private static List<Fiche> allFiches;
    private static List<Theme> allThemes;


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new ReceivedCookiesInterceptor(getApplicationContext()))
                    .addInterceptor(new AddCookiesInterceptor(getApplicationContext()))
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://fiche-and-frise-api.herokuapp.com/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }
        return retrofit;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static void setContext(Context context) {
        applicationContext = context;
    }

    public static Context getApplicationContext(){
        return applicationContext;
    }

    public static FicheDisplayRepository getFicheDisplayRepository() {
        if (ficheDisplayRepository == null) {
            ficheDisplayRepository = new FicheDisplayDataRepository(
                    new FicheDisplayRemoteDataSource(getFicheDisplayService())
            );
        }
        return ficheDisplayRepository;
    }

    public static FicheDisplayService getFicheDisplayService() {
        if (ficheDisplayService == null) {
            ficheDisplayService = getRetrofit().create(FicheDisplayService.class);
        }
        return ficheDisplayService;
    }

    public static FriseDisplayRepository getFriseDisplayRepository() {
        if (friseDisplayRepository == null) {
            friseDisplayRepository = new FriseDisplayDataRepository(
                    new FriseDisplayRemoteDataSource(getFriseDisplayService())
            );
        }
        return friseDisplayRepository;
    }

    public static FriseDisplayService getFriseDisplayService() {
        if (friseDisplayService == null) {
            friseDisplayService = getRetrofit().create(FriseDisplayService.class);
        }
        return friseDisplayService;
    }

    public static ThemeDisplayRepository getThemeDisplayRepository() {
        if (themeDisplayRepository == null) {
            themeDisplayRepository = new ThemeDisplayDataRepository(
                    new ThemeDisplayRemoteDataSource(getThemeDisplayService())
            );
        }
        return themeDisplayRepository;
    }

    public static ThemeDisplayService getThemeDisplayService() {
        if (themeDisplayService == null) {
            themeDisplayService = getRetrofit().create(ThemeDisplayService.class);
        }
        return themeDisplayService;
    }

    public static LoginService getLoginService() {
        if (loginService == null) {
            loginService = getRetrofit().create(LoginService.class);
        }
        return loginService;
    }

    public static LoginRepository getLoginRepository(){
        if(loginRepository == null){
            loginRepository = new LoginRepository(new LoginDataSource(getLoginService()));
        }
        return loginRepository;
    }

    public static ViewModelFactory getViewModelFactory() {
        if (viewModelFactory == null) {
            viewModelFactory = new ViewModelFactory(getFicheDisplayRepository(), getThemeDisplayRepository(), getFriseDisplayRepository());
        }
        return viewModelFactory;
    }

    public static List<Fiche> getAllFiches() {
        return allFiches;
    }

    public static void setAllFiches(List<Theme> allThemes) {
        if(allThemes.size() > 0) {
            ArrayList<Fiche> newFiches = new ArrayList<>();
            for (Theme t : allThemes) {
                newFiches.addAll(t.getListFiches());
            }
            FakeDependencyInjection.allFiches = newFiches;
        }
    }

    public static List<Theme> getAllThemes(){ return allThemes; }

    public static void setAllThemes(List<Theme> themes) {
        FakeDependencyInjection.allThemes = themes;
    }
}
