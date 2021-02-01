package com.example.fichefrise.data;

import android.util.Log;

import com.example.fichefrise.data.api.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            if(username.equals("maxime") && password.equals("loool")){
                Log.i("LOGIN DATASOURCE", "Utilisateur validé");
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Maxime Hilberer");
                return new Result.Success<>(fakeUser);
            }
            return new Result.Error(new IOException("Error logging in"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}