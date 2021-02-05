package com.example.fichefrise.presentation.display.frise;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;

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
import com.example.fichefrise.presentation.display.fiche.adapter.FicheActionInterface;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheAdapter;
import com.example.fichefrise.presentation.display.fiche.adapter.ThemeAdapter;
import com.example.fichefrise.presentation.display.frise.adapter.FriseActionInterface;
import com.example.fichefrise.presentation.display.frise.adapter.FriseAdapter;
import com.example.fichefrise.presentation.display.frise.adapter.ThemeFriseAdapter;
import com.example.fichefrise.presentation.viewmodel.FicheViewModel;
import com.example.fichefrise.presentation.viewmodel.FriseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
        setContentView(R.layout.activity_fiches_list);
        toolbar = findViewById(R.id.toolbarFichesList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Liste des frises");

        setupRecyclerView();
        registerViewModel();
    }

    private void registerViewModel() {
        friseViewModel = new ViewModelProvider(this, FakeDependencyInjection.getViewModelFactory()).get(FriseViewModel.class);
        friseViewModel.getAllThemes();
        friseViewModel.getThemes().observe(this, themes -> {
            allThemes = themes;
            themeAdapter.bindFicheViewModelList(themes);
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

    }
}
