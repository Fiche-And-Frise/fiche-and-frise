package com.example.fichefrise.presentation.display.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fichefrise.data.LoginRepository;
import com.example.fichefrise.data.Result;
import com.example.fichefrise.data.SaveSharedPreference;
import com.example.fichefrise.data.api.model.LoggedInUser;
import com.example.fichefrise.R;
import com.example.fichefrise.data.di.FakeDependencyInjection;

import java.util.List;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private CompositeDisposable compositeDisposable;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
        this.compositeDisposable = new CompositeDisposable();
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        compositeDisposable.clear();
        compositeDisposable.add(loginRepository.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<LoginResult>(){

                    @Override
                    public void onSuccess(@NonNull LoginResult result) {
                        Log.i("ON SUCCESS", result.toString());
                        SaveSharedPreference.setUserName(FakeDependencyInjection.getApplicationContext(), username);
                        SaveSharedPreference.setPassword(FakeDependencyInjection.getApplicationContext(), password);
                        loginResult.setValue(true);
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Bienvenue !", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("ON ERROR", e.toString());
                        loginResult.setValue(false);
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Mauvais identifiants", Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("ON COMPLETE", "Completed");
                    }
                }));
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return username.trim().length() >= 3;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 3;
    }
}