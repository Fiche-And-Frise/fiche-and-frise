package com.example.fichefrise.presentation.display.frise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.NewEvenementRequest;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.data.repository.FriseDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.DetailFicheActivity;
import com.example.fichefrise.presentation.display.fiche.FichesListActivity;
import com.example.fichefrise.presentation.display.frise.adapter.EvenementAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import yuku.ambilwarna.AmbilWarnaDialog;

import static com.example.fichefrise.presentation.display.fiche.FichesListActivity.FICHES_UPDATED;

public class FriseDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Frise frise;
    private int theme;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EvenementAdapter evenementAdapter;
    private String newEvenementName;
    private String newEvenementDate;
    private ArrayList<String> evenementsNames;
    Spinner spin;
    private int selectedEvenementIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frise_details);
        frise = (Frise) getIntent().getSerializableExtra("frise");
        theme = frise.getCurrentTheme();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(frise.getNomFrise());

        setupTextViews();
        setupRecyclerview();

        setupFabs();

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
        beginView.setText(String.valueOf(frise.getDateDebutFrise()));
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

    private void setupFabs() {
        FloatingActionButton fab = findViewById(R.id.fab_add_evenement);
        fab.setOnClickListener(v -> {
            createDialog();
        });

        FloatingActionButton fab2 = findViewById(R.id.fabDeleteFrise);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFrise();
            }
        });

    }

    private void createDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FriseDetailsActivity.this);
        final View evenementPopupView = getLayoutInflater().inflate(R.layout.evenement_popup, null);
        TextInputEditText newEvenementNameEditText = evenementPopupView.findViewById(R.id.evenement_name_edittext);
        TextInputEditText newEvenementDateEditText = evenementPopupView.findViewById(R.id.evenement_date_edittext);
        Button newEvenementSaveButton = evenementPopupView.findViewById(R.id.new_evenement_validate_button);
        Button newEvenementCancelButton = evenementPopupView.findViewById(R.id.new_evenement_cancel_button);
        spin = evenementPopupView.findViewById(R.id.evenement_spinner);
        setupSpinner(frise.getListEvenements());

        dialogBuilder.setView(evenementPopupView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        newEvenementSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ON CLICK", "Here");
                newEvenementName = newEvenementNameEditText.getText().toString();
                newEvenementDate = "";
                try {
                    Log.i("ON CLICK", "Try here");
                    newEvenementDate = newEvenementDateEditText.getText().toString();
                } catch (Exception e){
                    Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez entrer une date valide", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                Log.i("DATA IS CORRECT", "Before validation");
                if(newEvenementName.length()>0 && newEvenementDate.length() > 0){
                    Log.i("DATA IS CORRECT", "Here");
                    saveNewEvenement();
                    dialog.dismiss();
                } else {
                    Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez renseigner un nom et une date valide", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        newEvenementCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void deleteFrise(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        FriseDisplayRepository repo = FakeDependencyInjection.getFriseDisplayRepository();
                        CompositeDisposable compositeDisposable = new CompositeDisposable();
                        compositeDisposable.clear();
                        compositeDisposable.add(repo.deleteFrise(frise.getFriseId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableCompletableObserver(){

                                    @Override
                                    public void onComplete() {
                                        setResult(FichesListActivity.FICHES_UPDATED);
                                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Frise supprimée", Toast.LENGTH_SHORT)
                                                .show();
                                        finish();
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.e("ERROR", e.toString());
                                    }
                                }));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(FriseDetailsActivity.this);
        builder.setMessage("Supprimer la frise ?").setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
    }

    private void saveNewEvenement() {
        Log.i("CREATING EVENEMENT", "Beginning");
        Evenement newEvenement = new Evenement(newEvenementName, newEvenementDate);
        frise.getListEvenements().remove(frise.getListEvenements().size()-1);
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
                        setupRecyclerview();
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Evènement créé", Toast.LENGTH_SHORT)
                                .show();
                        setResult(FICHES_UPDATED);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("CREATING FRISE ERROR", e.toString());
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Erreur lors de la création de l'évènement", Toast.LENGTH_SHORT)
                                .show();
                    }
                }));
    }

    private void setupSpinner(List<Evenement> allEvenements) {
        evenementsNames = new ArrayList<>();
        evenementsNames.add("Il s'agit du premier événement");
        for(Evenement e : allEvenements){
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedEvenementIndex = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}