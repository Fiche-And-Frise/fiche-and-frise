package com.example.fichefrise.presentation.display;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import com.example.fichefrise.R;
import com.example.fichefrise.data.LoginDataSource;
import com.example.fichefrise.data.LoginRepository;
import com.example.fichefrise.data.SaveSharedPreference;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.presentation.display.fiche.CreateFicheActivity;
import com.example.fichefrise.presentation.display.fiche.FichesListActivity;
import com.example.fichefrise.presentation.display.fiche.DetailFicheActivity;
import com.example.fichefrise.presentation.display.login.LoginActivity;
import com.example.fichefrise.presentation.display.login.LoginResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ImageButton ficheButton, friseButton;
    private final LoginRepository loginRepo = FakeDependencyInjection.getLoginRepository();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Context ctx = FakeDependencyInjection.getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            // call Login Activity
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        ficheButton = findViewById(R.id.fiche_button);
        ficheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FichesListActivity.class);
                startActivity(i);
                finish();
            }
        });

        friseButton = findViewById(R.id.frise_button);
        friseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.fabMainActivity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compositeDisposable.clear();
                compositeDisposable.add(loginRepo.logout()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver(){

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i("ON ERROR", e.toString());
                            }

                            @Override
                            public void onComplete() {
                                Log.i("ON COMPLETE", "Completed");
                                SaveSharedPreference.clearUserName(ctx);
                                Intent i = new Intent(ctx, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }));
            }
        });

    }
}