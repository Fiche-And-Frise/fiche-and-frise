package com.example.fichefrise.presentation.display.frise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.presentation.display.frise.adapter.EvenementAdapter;

public class FriseDetailsActivity extends AppCompatActivity {

    private Frise frise;
    private Theme theme;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EvenementAdapter evenementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frise_details);
        frise = (Frise) getIntent().getSerializableExtra("frise");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Aperçu d'une frise");

        setupTextViews();
        setupRecyclerview();

    }

    private void setupRecyclerview() {
        recyclerView = findViewById(R.id.evenements_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        evenementAdapter = new EvenementAdapter();
        evenementAdapter.bindViewModelList(frise.getListEvenements());
        recyclerView.setAdapter(evenementAdapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void setupTextViews() {
        TextView beginView = findViewById(R.id.begin_date_textview);
        TextView endView = findViewById(R.id.end_date_textview);
        beginView.setText(String.valueOf(frise.getDateDebutFrise()));
        endView.setText(String.valueOf(frise.getDateFinFrise()));
    }
}