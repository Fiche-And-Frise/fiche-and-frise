package com.example.fichefrise.data;

import android.content.Context;
import android.util.Log;

import com.example.fichefrise.data.api.LoginService;
import com.example.fichefrise.data.api.model.LoggedInUser;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.presentation.display.login.LoginResult;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private LoginService loginService;
    private Boolean result = false;
    private Context ctx = FakeDependencyInjection.getApplicationContext();

    public LoginDataSource(LoginService loginService){
        this.loginService = loginService;
    }

    public Maybe<LoginResult> login(String username, String password) {
        // TODO: handle loggedInUser authentication
        Log.i("LOG IN", "Sending request");
        return this.loginService.login(username, password);
    }

    public Completable logout(){
        // TODO: revoke authentication
        Log.i("LOG OUT", "Sending request");
        return this.loginService.logout();
    }
}