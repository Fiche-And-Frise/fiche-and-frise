package com.example.fichefrise.presentation.display.fiche;

import android.os.Bundle;

import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheAdapter;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheViewItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.example.fichefrise.R;

import java.util.ArrayList;
import java.util.List;

public class FichesListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FicheAdapter adapter;
    private List<FicheViewItem> fichesListStatic = new ArrayList<>();

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiches_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Liste des fiches");

        createFiches();

        setupRecyclerView();
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void setupRecyclerView() {
        recyclerView =findViewById(R.id.fiches_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        adapter = new FicheAdapter();
        adapter.bindFicheViewModelList(fichesListStatic);
        recyclerView.setAdapter(this.adapter);
        recyclerView.setLayoutManager(this.layoutManager);
    }

    private void createFiches(){
        ArrayList<FicheViewItem> list = new ArrayList<>();
        FicheViewItem f1 = new FicheViewItem();
        f1.setFicheId(1);
        f1.setNomFiche("La première fiche");
        list.add(f1);

        FicheViewItem f2 = new FicheViewItem();
        f2.setFicheId(2);
        f2.setNomFiche("La deuxième fiche");
        list.add(f2);

        FicheViewItem f3 = new FicheViewItem();
        f3.setFicheId(3);
        f3.setNomFiche("La première guerre mondiale");
        list.add(f3);

        this.fichesListStatic = list;

    }
}