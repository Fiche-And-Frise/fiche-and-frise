package com.example.fichefrise.presentation.display.fiche;

import android.content.Intent;
import android.os.Bundle;

import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheActionInterface;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheAdapter;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheViewItem;
import com.example.fichefrise.presentation.display.fiche.adapter.ThemeAdapter;
import com.example.fichefrise.presentation.viewmodel.FicheViewModel;
import com.example.fichefrise.presentation.viewmodel.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.fichefrise.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FichesListActivity extends AppCompatActivity implements FicheActionInterface {

    public static final int FICHES_UPDATED = 111;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ThemeAdapter themeAdapter;
    private FicheAdapter ficheAdapter;
    private FicheViewModel ficheViewModel;
    private List<Theme> allThemes = new ArrayList<>();
    private static int sort = 0;

    private Toolbar toolbar;
    private ImageView btn_alpha_theme_sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiches_list);
        toolbar = findViewById(R.id.toolbarFichesList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Liste des fiches");

        setupRecyclerView();
        registerViewModel();

        FloatingActionButton fab = findViewById(R.id.fabListFiche);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(FichesListActivity.this, CreateFicheActivity.class);
                ArrayList<Theme> myThemes = new ArrayList<>(allThemes);
                Log.i("ON CLICK", "On est ici : " + myThemes.size());
                i.putExtra("allThemes", myThemes);
                startActivityForResult(i, 150);
            }
        });
    }

    private void registerViewModel(){
        Log.i("DANS LE REGISTER", "On est ici !");
        ficheViewModel = new ViewModelProvider(this, FakeDependencyInjection.getViewModelFactory()).get(FicheViewModel.class);
        ficheViewModel.getAllThemes();
        ficheViewModel.getThemes().observe(this, themes -> {
            allThemes = themes;
            themeAdapter.bindFicheViewModelList(themes);
            ficheAdapter.bindFicheViewModelList(themes);
            });
        Log.i("DANS LE REGISTER", "On a terminé !");
    }

    private void setupRecyclerView() {
        recyclerView =findViewById(R.id.themes_recyclerview);

        layoutManager = new LinearLayoutManager(this);

        ficheAdapter = new FicheAdapter(this);
        //ficheAdapter.bindFicheViewModelList(fichesListStatic);

        themeAdapter = new ThemeAdapter(this);
        //themeAdapter.bindFicheViewModelList(allThemes);

        recyclerView.setAdapter(this.themeAdapter);
        recyclerView.setLayoutManager(this.layoutManager);

        btn_alpha_theme_sort = findViewById(R.id.image_alpha_theme_sort);

        btn_alpha_theme_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sort == 0){
                    btn_alpha_theme_sort.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_theme_24));
                    recyclerView.setAdapter(ficheAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                    sort = 1;
                }else{
                    btn_alpha_theme_sort.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_sort_by_alpha_24));
                    recyclerView.setAdapter(themeAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                    sort = 0;
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // back button
        if (id == android.R.id.home) {
            Log.i("Item selected", "On est passé là !");
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFicheClicked(Fiche fiche) {
        Intent i = new Intent(this, DetailFicheActivity.class);
        Log.i("FICHE CLICKED", "Fiche clicked : " + fiche.toString());
        i.putExtra("fiche", fiche);
        Theme currentTheme = null;
        for(Theme t : allThemes){
            if(t.getThemeId() == fiche.getThemeId()){
                currentTheme = t;
            }
        }
        i.putExtra("theme", currentTheme);
        startActivityForResult(i, 150);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == FICHES_UPDATED){
            this.ficheViewModel.getAllThemes();
        }
    }
}