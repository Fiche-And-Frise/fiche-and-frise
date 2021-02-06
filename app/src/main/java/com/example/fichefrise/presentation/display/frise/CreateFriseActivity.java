package com.example.fichefrise.presentation.display.frise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.NewFriseRequest;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.data.repository.FriseDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.CreateFicheActivity;
import com.example.fichefrise.presentation.display.fiche.DetailFicheActivity;
import com.example.fichefrise.presentation.display.fiche.FichesListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateFriseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ArrayList<Theme> allThemes;
    private ArrayList<String> themesNames = new ArrayList<>();
    private AlertDialog dialog;
    private EditText newThemeNameEditText, newFriseNameEditText, beginDateEditText, endDateEditText;
    private ImageButton colorPickerButton;
    private int themeColor = 0;
    private String newThemeName = "";
    private Theme selectedTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_frise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Création d'une frise");

        newFriseNameEditText = findViewById(R.id.edit_frise_name);
        beginDateEditText = findViewById(R.id.begin_edittext);
        endDateEditText = findViewById(R.id.end_edittext);

        allThemes = (ArrayList<Theme>) getIntent().getSerializableExtra("allThemes");
        setupSpinner(allThemes);
        setupAddThemeButton();

        FloatingActionButton fab = findViewById(R.id.fabCreateFrise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewFrise();
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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CreateFriseActivity.this);
        final View themePopupView = getLayoutInflater().inflate(R.layout.theme_popup, null);
        newThemeNameEditText = themePopupView.findViewById(R.id.new_theme_name_editText);
        Button newThemeSaveButton = themePopupView.findViewById(R.id.new_theme_validate_button);
        Button newThemeCancelButton = themePopupView.findViewById(R.id.new_theme_cancel_button);
        colorPickerButton = themePopupView.findViewById(R.id.color_picker);

        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(CreateFriseActivity.this, themeColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTheme = allThemes.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void createNewFrise(){
        String friseName = newFriseNameEditText.getText().toString();
        if(friseName.length() < 1){
            Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez entrer un nom de frise", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        int begin = 0;
        int end = 0;
        try {
            begin = Integer.parseInt(beginDateEditText.getText().toString());
            end = Integer.parseInt(endDateEditText.getText().toString());
        } catch (Exception e){
            Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez entrer 2 années valides", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Frise frise = new Frise(friseName, begin, end);
        Theme theme = selectedTheme;

        FriseDisplayRepository repo = FakeDependencyInjection.getFriseDisplayRepository();
        NewFriseRequest request = new NewFriseRequest(frise, theme);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.clear();
        compositeDisposable.add(repo.createNewFrise(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Frise>(){

                    @Override
                    public void onSuccess(@NonNull Frise frise) {
                        Log.i("CREATING FRISE", "Ca s'est bien passé !");
                        Intent i = new Intent(CreateFriseActivity.this, FriseDetailsActivity.class);
                        i.putExtra("frise", frise);
                        i.putExtra("theme", selectedTheme);
                        startActivity(i);
                        setResult(FichesListActivity.FICHES_UPDATED);
                        finish();
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Frise créée", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("CREATING FRISE ERROR", e.toString());
                    }
                }));
    }
}