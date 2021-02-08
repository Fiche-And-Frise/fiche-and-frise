package com.example.fichefrise.presentation.display.frise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.NewEvenementRequest;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.data.repository.FriseDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.FichesListActivity;
import com.example.fichefrise.presentation.display.frise.adapter.EvenementAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import yuku.ambilwarna.AmbilWarnaDialog;

public class FriseDetailsActivity extends AppCompatActivity {

    private Frise frise;
    private Theme theme;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EvenementAdapter evenementAdapter;
    private String newEvenementName;
    private int newEvenementDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frise_details);
        frise = (Frise) getIntent().getSerializableExtra("frise");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(frise.getNomFrise());

        setupTextViews();
        setupRecyclerview();

        setupFab();

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

    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fab_add_evenement);
        fab.setOnClickListener(v -> {
            createDialog();
        });
    }

    private void createDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FriseDetailsActivity.this);
        final View evenementPopupView = getLayoutInflater().inflate(R.layout.evenement_popup, null);
        TextInputEditText newEvenementNameEditText = evenementPopupView.findViewById(R.id.evenement_name_edittext);
        TextInputEditText newEvenementDateEditText = evenementPopupView.findViewById(R.id.evenement_date_edittext);
        Button newEvenementSaveButton = evenementPopupView.findViewById(R.id.new_evenement_validate_button);
        Button newEvenementCancelButton = evenementPopupView.findViewById(R.id.new_evenement_cancel_button);

        dialogBuilder.setView(evenementPopupView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        newEvenementSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ON CLICK", "Here");
                newEvenementName = newEvenementNameEditText.getText().toString();
                newEvenementDate = 0;
                try {
                    Log.i("ON CLICK", "Try here");
                    newEvenementDate = Integer.parseInt(newEvenementDateEditText.getText().toString());
                } catch (Exception e){
                    Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez entrer une date valide", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                Log.i("DATA IS CORRECT", "Before validation");
                if(newEvenementName.length()>0 && newEvenementDate != 0){
                    Log.i("DATA IS CORRECT", "Here");
                    saveNewEvenement();
                    dialog.dismiss();
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

    private void saveNewEvenement() {
        Log.i("CREATING EVENEMENT", "Beginning");
        Evenement newEvenement = new Evenement(newEvenementName, newEvenementDate);
        NewEvenementRequest request = new NewEvenementRequest(frise, theme, newEvenement);

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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("CREATING FRISE ERROR", e.toString());
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Erreur lors de la création de l'évènement", Toast.LENGTH_SHORT)
                                .show();
                    }
                }));
    }

}