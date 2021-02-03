package com.example.fichefrise.data.di;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    public static final String PREF_COOKIES = "PREF_COOKIES";
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> preferences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES, new HashSet<String>());

        Request original = chain.request();
        Log.i("INTERCEPTOR", "URL : " + original.url().toString());
        if(original.url().toString().contains("https://fiche-and-frise-api.herokuapp.com")){
            for (String cookie : preferences) {
                Log.i("INTERCEPTOR", "Cookies added to request : " + cookie);
                builder.addHeader("Cookie", cookie);
            }
        }

        return chain.proceed(builder.build());
    }
}