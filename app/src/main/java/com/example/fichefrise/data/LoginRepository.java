package com.example.fichefrise.data;

import com.example.fichefrise.data.api.model.LoggedInUser;
import com.example.fichefrise.presentation.display.login.LoginResult;

import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import okhttp3.ResponseBody;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    public LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Maybe<LoginResult> login(String username, String password) {
        // handle login
        return this.dataSource.login(username, password);
    }

    public Completable logout() {
        user = null;
        return this.dataSource.logout();
    }

}