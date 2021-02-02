package com.example.fichefrise.data.api;

import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.presentation.display.login.LoginResult;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("login")
    Maybe<LoginResult> login(@Field("username") String username, @Field("password") String password);

    @POST("logout")
    Completable logout();

}
