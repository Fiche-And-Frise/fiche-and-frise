package com.example.fichefrise.presentation.display;

import androidx.annotation.NonNull;
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
import com.example.fichefrise.data.SaveSharedPreference;
import com.example.fichefrise.presentation.display.fiche.FichesListActivity;
import com.example.fichefrise.presentation.display.fiche.DetailFicheActivity;
import com.example.fichefrise.presentation.display.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton ficheButton, friseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            // call Login Activity
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }

        ficheButton = findViewById(R.id.fiche_button);
        ficheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailFicheActivity.class);
                startActivity(i);
            }
        });

        friseButton = findViewById(R.id.frise_button);
        friseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FichesListActivity.class);
                startActivity(i);
            }
        });

    }
}