package com.example.fichefrise.presentation.display.frise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.NewEvenementRequest;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.data.repository.FriseDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.fragment.CreateFicheFragment;
import com.example.fichefrise.presentation.display.frise.fragment.CreateEvenementFragment;
import com.example.fichefrise.presentation.display.frise.fragment.ImportEvenementFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.fichefrise.presentation.display.fiche.FichesListActivity.FICHES_UPDATED;
import static com.example.fichefrise.presentation.display.frise.FriseDetailsActivity.FRISE_UPDATED;

public class CreateEvenementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String newEvenementName, newEvenementDate;
    private Frise frise;
    private int theme;
    private int selectedEvenementIndex;
    private ViewPager viewPager;
    private List<Evenement> evenements;
    private List<String> evenementsNames;
    private CreateEvenementFragment fragmentCreate;
    private ImportEvenementFragment fragmentImport;
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_evenement);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Création d'un événement");
        frise = (Frise) getIntent().getSerializableExtra("frise");
        evenements = frise.getListEvenements();
        theme = getIntent().getIntExtra("theme", 0);
        setupViewPagerAndTabs();
        spin = findViewById(R.id.evenement_spinner);
        setupSpinner();
        setupFab();
    }

    private void setupViewPagerAndTabs() {

        viewPager = findViewById(R.id.view_pager);
        fragmentCreate = CreateEvenementFragment.newInstance();
        fragmentImport = ImportEvenementFragment.newInstance(FakeDependencyInjection.getAllFiches());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 2;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return fragmentCreate;
                } else {
                    return fragmentImport;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return fragmentCreate.getName();
                }
                return fragmentImport.getName();
            }
        });
    }

    private void setupSpinner() {
        evenementsNames = new ArrayList<>();
        evenementsNames.add("Il s'agit du premier événement");
        for(Evenement e : evenements){
            if(e != null)
                this.evenementsNames.add(e.getNomEvenement());
        }

        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the evenements list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,this.evenementsNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    private void setupFab(){
        FloatingActionButton fab = findViewById(R.id.fab_create_evenement);
        fab.setOnClickListener(v -> {
            saveNewEvenement();

        });
    }

    private void saveNewEvenement() {
        Log.i("CREATING EVENEMENT", "Beginning : " + selectedEvenementIndex);
        frise.getListEvenements().remove(frise.getListEvenements().size()-1);
        Evenement newEvenement;
        if(viewPager.getCurrentItem() == 0){
            newEvenementName = fragmentCreate.getEvenementName();
            newEvenementDate = fragmentCreate.getEvenementDate();
            if(newEvenementName.length() < 1 || newEvenementDate.length() < 1){
                Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez renseigner un nom et une date", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
           newEvenement = new Evenement(newEvenementName, newEvenementDate);
        } else {
            newEvenementDate = fragmentImport.getEvenementDate();
            if(newEvenementDate.length() < 1){
                Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez renseigner une date", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            int ficheId = -1, themeId = -1;
            List<Theme> themeList = FakeDependencyInjection.getAllThemes();
            for(Theme t : themeList){
                for(Fiche f : t.getListFiches()) {
                    if(f.getNomFiche().equals(fragmentImport.getSelectedFiche())) {
                        ficheId = f.getFicheId();
                        themeId = t.getThemeId();
                        newEvenementName = f.getNomFiche();
                    }
                }
            }
            newEvenement = new Evenement(newEvenementName, newEvenementDate, ficheId, themeId);
        }

        NewEvenementRequest request = new NewEvenementRequest(frise, theme, newEvenement, selectedEvenementIndex);

        FriseDisplayRepository repo = FakeDependencyInjection.getFriseDisplayRepository();

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.clear();
        compositeDisposable.add(repo.createNewEvenement(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Frise>(){

                    @Override
                    public void onSuccess(@NonNull Frise responseFrise) {
                        Log.i("CREATING EVENEMENT", "Ca s'est bien passé !");
                        frise = responseFrise;
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Evènement créé", Toast.LENGTH_SHORT)
                                .show();
                        Intent i = new Intent(CreateEvenementActivity.this, FriseDetailsActivity.class);
                        i.putExtra("frise", responseFrise);
                        setResult(FRISE_UPDATED, i);
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("CREATING FRISE ERROR", e.toString());
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Erreur lors de la création de l'évènement", Toast.LENGTH_SHORT)
                                .show();
                    }
                }));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedEvenementIndex = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}