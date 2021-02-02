package com.example.fichefrise.presentation.display.login;

import com.google.gson.annotations.SerializedName;

import java.security.Timestamp;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    @SerializedName("message")
    private String message;
    @SerializedName("username")
    private String username;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "LoginResult{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}