package com.example.fichefrise;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.fichefrise.data.LoginRepository;
import com.example.fichefrise.data.SaveSharedPreference;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.presentation.display.MainActivity;
import com.example.fichefrise.presentation.display.login.LoginResult;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class FicheAndFriseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FakeDependencyInjection.setContext(this);
        if(SaveSharedPreference.getUserName(this).length() != 0)
        {
            String username = SaveSharedPreference.getUserName(this);
            String password = SaveSharedPreference.getPassword(this);
            LoginRepository repo = FakeDependencyInjection.getLoginRepository();
            CompositeDisposable compositeDisposable = new CompositeDisposable();

            compositeDisposable.clear();
            compositeDisposable.add(repo.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableMaybeObserver<LoginResult>(){

                        @Override
                        public void onSuccess(@NonNull LoginResult result) {
                            Log.i("ON SUCCESS", result.toString());
                            Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Bienvenue !", Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.i("ON ERROR", e.toString());
                            Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Connexion au serveur échouée", Toast.LENGTH_LONG)
                                    .show();
                        }

                        @Override
                        public void onComplete() {
                            Log.i("ON COMPLETE", "Completed");
                        }
                    }));
        }
    }
}
