package com.example.fichefrise.presentation.display.fiche;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.NewFicheRequest;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.data.repository.FicheDisplayDataRepository;
import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.fragment.CreateFicheFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateFicheActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Theme> allThemes;
    private ArrayList<String> themesNames = new ArrayList<>();
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText newThemeNameEditText, newFicheNameEditText;
    private Button newThemeSaveButton, newThemeCancelButton;
    private ImageButton colorPickerButton;
    private int themeColor = 0;
    private String newThemeName = "";
    private CreateFicheFragment fragmentRecto, fragmentVerso;
    private Theme selectedTheme;
    private FloatingActionButton fab;

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_fiche);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Création d'une fiche");

        newFicheNameEditText = findViewById(R.id.edit_fiche_name);

        allThemes = (ArrayList<Theme>) getIntent().getSerializableExtra("allThemes");
        setupSpinner(allThemes);
        setupAddThemeButton();

        setupViewPagerAndTabs();

        fab = findViewById(R.id.fabCreateFiche);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(CreateFicheActivity.this, FichesListActivity.class);
                //startActivity(i);
                fab.setEnabled(false);
                createNewFiche();
            }
        });

    }

    private void setupAddThemeButton() {
        ImageButton addThemeButton = findViewById(R.id.add_theme_button);

        addThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

    }

    private void createDialog(){
        dialogBuilder = new AlertDialog.Builder(CreateFicheActivity.this);
        final View themePopupView = getLayoutInflater().inflate(R.layout.theme_popup, null);
        newThemeNameEditText = themePopupView.findViewById(R.id.new_theme_name_editText);
        newThemeSaveButton = themePopupView.findViewById(R.id.new_theme_validate_button);
        newThemeCancelButton = themePopupView.findViewById(R.id.new_theme_cancel_button);
        colorPickerButton = themePopupView.findViewById(R.id.color_picker);

        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(CreateFicheActivity.this, themeColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        themeColor = color;
                        Log.i("COLOR", "New color : " + themeColor);
                        colorPickerButton.setBackgroundColor(themeColor);
                    }
                });
                ambilWarnaDialog.show();
            }
        });

        dialogBuilder.setView(themePopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        newThemeSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newThemeName = newThemeNameEditText.getText().toString();
                if(newThemeName.length()>0){
                    allThemes.add(new Theme(newThemeName, themeColor));
                    setupSpinner(allThemes);
                }
                dialog.dismiss();
            }
        });

        newThemeCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setupSpinner(ArrayList<Theme> allThemes) {
        themesNames = new ArrayList<>();
        for(Theme t : allThemes){
            this.themesNames.add(t.getNomTheme());
        }
        Spinner spin = findViewById(R.id.theme_spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the themes list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,this.themesNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    private void setupViewPagerAndTabs() {

        viewPager = findViewById(R.id.view_pager);
        fragmentRecto = CreateFicheFragment.newInstance(0);
        fragmentVerso = CreateFicheFragment.newInstance(1);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 2;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return fragmentRecto;
                } else {
                    return fragmentVerso;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return fragmentRecto.getName();
                }
                return fragmentVerso.getName();
            }
        });
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTheme = allThemes.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void createNewFiche(){
        Log.i("CREATING FICHE", "Beginning");
        String ficheName = newFicheNameEditText.getText().toString();
        if(ficheName.length() < 1){
            Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez entrer un nom de fiche", Toast.LENGTH_SHORT)
                    .show();
            fab.setEnabled(true);
            return;
        }
        String ficheRecto = fragmentRecto.getContent();
        String ficheVerso = fragmentVerso.getContent();
        Fiche newFiche = new Fiche(ficheName, ficheRecto, ficheVerso);
        Theme ficheTheme = selectedTheme;

        FicheDisplayRepository repo = FakeDependencyInjection.getFicheDisplayRepository();
        NewFicheRequest request = new NewFicheRequest(newFiche, ficheTheme);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.clear();
        compositeDisposable.add(repo.createNewFiche(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Fiche>(){

                    @Override
                    public void onSuccess(@NonNull Fiche fiche) {
                        Log.i("CREATING FICHE", "Ca s'est bien passé !");
                        Intent i = new Intent(CreateFicheActivity.this, DetailFicheActivity.class);
                        i.putExtra("fiche", fiche);
                        i.putExtra("theme", selectedTheme);
                        startActivity(i);
                        setResult(FichesListActivity.FICHES_UPDATED);
                        finish();
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Fiche créée", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("CREATING FICHE ERROR", e.toString());
                        fab.setEnabled(true);
                    }
                }));

    }
}