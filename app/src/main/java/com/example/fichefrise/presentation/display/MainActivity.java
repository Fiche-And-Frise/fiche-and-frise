package com.example.fichefrise.presentation.display;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import com.example.fichefrise.R;
import com.example.fichefrise.presentation.display.fiche.DetailFicheActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton ficheButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ficheButton = findViewById(R.id.fiche_button);
        ficheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DetailFicheActivity.class);
                startActivity(i);
            }
        });

    }

}