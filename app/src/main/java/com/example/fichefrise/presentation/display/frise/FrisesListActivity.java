package com.example.fichefrise.presentation.display.frise;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.presentation.display.fiche.CreateFicheActivity;
import com.example.fichefrise.presentation.display.fiche.FichesListActivity;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheActionInterface;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheAdapter;
import com.example.fichefrise.presentation.display.fiche.adapter.ThemeAdapter;
import com.example.fichefrise.presentation.display.frise.adapter.FriseActionInterface;
import com.example.fichefrise.presentation.display.frise.adapter.FriseAdapter;
import com.example.fichefrise.presentation.display.frise.adapter.ThemeFriseAdapter;
import com.example.fichefrise.presentation.viewmodel.FicheViewModel;
import com.example.fichefrise.presentation.viewmodel.FriseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.fichefrise.presentation.display.fiche.FichesListActivity.FICHES_UPDATED;


public class FrisesListActivity extends AppCompatActivity implements FriseActionInterface {

    private List<Theme> allThemes = new ArrayList<>();
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ThemeFriseAdapter themeAdapter;
    private FriseAdapter friseAdapter;
    private FriseViewModel friseViewModel;
    private ImageView btn_alpha_theme_sort;
    private boolean sort = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frises_list);
        toolbar = findViewById(R.id.toolbarFrisesList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Liste des frises");

        setupRecyclerView();
        registerViewModel();

        FloatingActionButton fab = findViewById(R.id.fabListFrise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent i = new Intent(FrisesListActivity.this, CreateFriseActivity.class);
                ArrayList<Theme> myThemes = new ArrayList<>(allThemes);
                i.putExtra("allThemes", myThemes);
                startActivityForResult(i, 350);
            }
        });
    }

    private void registerViewModel() {
        friseViewModel = new ViewModelProvider(this, FakeDependencyInjection.getViewModelFactory()).get(FriseViewModel.class);
        friseViewModel.getAllThemes();
        friseViewModel.getThemes().observe(this, themes -> {
            allThemes = themes;
            FakeDependencyInjection.setAllFiches(themes);
            FakeDependencyInjection.setAllThemes(themes);
            themeAdapter.bindFriseViewModelList(themes);
            friseAdapter.bindFriseViewModelList(themes);
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.themes_recyclerview);

        layoutManager = new LinearLayoutManager(this);

        friseAdapter = new FriseAdapter(this);
        //ficheAdapter.bindFicheViewModelList(fichesListStatic);

        themeAdapter = new ThemeFriseAdapter(this);
        //themeAdapter.bindFicheViewModelList(allThemes);

        recyclerView.setAdapter(this.themeAdapter);
        recyclerView.setLayoutManager(this.layoutManager);

        btn_alpha_theme_sort = findViewById(R.id.image_alpha_theme_sort);

        btn_alpha_theme_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sort){
                    btn_alpha_theme_sort.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_theme_24));
                    recyclerView.setAdapter(friseAdapter);
                }else{
                    btn_alpha_theme_sort.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_sort_by_alpha_24));
                    recyclerView.setAdapter(themeAdapter);
                }
                recyclerView.setLayoutManager(layoutManager);
                sort = !sort;
            }
        });
    }

    @Override
    public void onFriseClicked(Frise frise) {
        Intent i = new Intent(FrisesListActivity.this, FriseDetailsActivity.class);
        i.putExtra("frise", frise);
        startActivityForResult(i, 500);
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
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == FICHES_UPDATED){
            this.friseViewModel.getAllThemes();
            setupRecyclerView();
        }
    }

}
