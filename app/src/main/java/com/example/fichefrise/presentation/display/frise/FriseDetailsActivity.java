package com.example.fichefrise.presentation.display.frise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.NewEvenementRequest;
import com.example.fichefrise.data.api.model.NewFriseRequest;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.data.repository.FriseDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.DetailFicheActivity;
import com.example.fichefrise.presentation.display.fiche.FichesListActivity;
import com.example.fichefrise.presentation.display.frise.adapter.EvenementActionInterface;
import com.example.fichefrise.presentation.display.frise.adapter.EvenementAdapter;
import com.example.fichefrise.presentation.display.frise.fragment.CreateEvenementFragment;
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

public class FriseDetailsActivity extends AppCompatActivity implements EvenementActionInterface {

    public static final int FRISE_UPDATED = 456;
    private Frise frise;
    private int theme;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EvenementAdapter evenementAdapter;
    private String newEvenementName;
    private String newEvenementDate;
    private ArrayList<String> evenementsNames;
    private Spinner spin;
    private int selectedEvenementIndex;
    private long mLastClickTime = 0;

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
        evenementAdapter = new EvenementAdapter(this, frise.getDateFinFrise());
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
            // mis-clicking prevention, using threshold of 1000 ms
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            Intent i = new Intent(FriseDetailsActivity.this, CreateEvenementActivity.class);
            i.putExtra("frise", frise);
            i.putExtra("theme", theme);
            startActivityForResult(i, 489);
        });

        FloatingActionButton fab2 = findViewById(R.id.fabDeleteFrise);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();deleteFrise();
            }
        });

    }

    private void deleteFrise(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // mis-clicking prevention, using threshold of 500 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == FRISE_UPDATED){
            frise = (Frise) data.getSerializableExtra("frise");
            setupRecyclerview();
        }
    }

    @Override
    public void onEvenementClicked(Evenement evenement) {
        selectedEvenementIndex = frise.getListEvenements().indexOf(evenement);
        if(evenement.getFicheId() != -1){
            Fiche fiche = null;
            Theme currentTheme = null;
            List<Fiche> allFiches = FakeDependencyInjection.getAllFiches();
            for(Fiche f : allFiches){
                if(f.getFicheId() == evenement.getFicheId()){
                    fiche = f;
                    break;
                }
            }
            List<Theme> allThemes = FakeDependencyInjection.getAllThemes();
            for(Theme t : allThemes){
                if(t.getThemeId() == evenement.getThemeId()){
                    currentTheme = t;
                    break;
                }
            }
            Intent i = new Intent(FriseDetailsActivity.this, DetailFicheActivity.class);
            i.putExtra("fiche", fiche);
            i.putExtra("theme", currentTheme);
            startActivityForResult(i, 777);
            return;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FriseDetailsActivity.this);
        final View evenementPopupView = getLayoutInflater().inflate(R.layout.evenement_popup, null);
        TextInputEditText evenementNameEditText = evenementPopupView.findViewById(R.id.evenement_name_edittext);
        evenementNameEditText.setEnabled(false);
        evenementNameEditText.setText(evenement.getNomEvenement());

        TextInputEditText evenementDateEditText = evenementPopupView.findViewById(R.id.evenement_date_edittext);
        evenementDateEditText.setEnabled(false);
        evenementDateEditText.setText(evenement.getDateDebutEvenement());
        Button evenementUpdateButton = evenementPopupView.findViewById(R.id.evenement_update_button);
        Button evenementDeleteButton = evenementPopupView.findViewById(R.id.evenement_delete_button);
        Button evenementValidateButton = evenementPopupView.findViewById(R.id.evenement_validate_button);

        dialogBuilder.setView(evenementPopupView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        evenementUpdateButton.setOnClickListener(v -> {
            evenementNameEditText.setEnabled(true);
            evenementDateEditText.setEnabled(true);
            evenementUpdateButton.setVisibility(View.GONE);
            evenementValidateButton.setVisibility(View.VISIBLE);
        });

        evenementDeleteButton.setOnClickListener(v -> {
            deleteEvenement(evenement);
        });

        evenementValidateButton.setOnClickListener( v -> {
            evenementValidateButton.setEnabled(false);
            String newName = evenementNameEditText.getText().toString();
            String newDate = evenementDateEditText.getText().toString();
            if(newName.length() < 1 || newDate.length() < 1){
                Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez entrer un nom et une date", Toast.LENGTH_SHORT)
                        .show();
                evenementValidateButton.setEnabled(true);
            } else {
                updateEvenement(evenement, newName, newDate);
                dialog.dismiss();
            }
        });
    }

    private void deleteEvenement(Evenement evenement) {
        List<Theme> allThemes = FakeDependencyInjection.getAllThemes();
        Theme currentTheme = null;
        for(Theme t : allThemes){
            if(t.getThemeId() == theme){
                currentTheme = t;
                break;
            }
        }
        //frise.getListEvenements().remove(frise.getListEvenements().size()-1);
        frise.getListEvenements().remove(evenement);
        NewFriseRequest request = new NewFriseRequest(frise, currentTheme);
        FriseDisplayRepository repo = FakeDependencyInjection.getFriseDisplayRepository();

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.clear();
        compositeDisposable.add(repo.deleteEvenement(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Frise>(){

                    @Override
                    public void onSuccess(@NonNull Frise responseFrise) {
                        Log.i("EVENEMENT DELETED", "Ca s'est bien passé !");
                        frise = responseFrise;
                        setupRecyclerview();
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Evènement supprimé", Toast.LENGTH_SHORT)
                                .show();
                        setResult(FICHES_UPDATED);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("DELETE EVENEMENT ERROR", e.toString());
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Erreur lors de la suppression de l'évènement", Toast.LENGTH_SHORT)
                                .show();
                    }
                }));
    }

    private void updateEvenement(Evenement evenement, String name, String date) {
        Log.i("CREATING EVENEMENT", "Beginning");
        //frise.getListEvenements().remove(frise.getListEvenements().size()-1);
        frise.getListEvenements().remove(evenement);
        evenement.setNomEvenement(name);
        evenement.setDateDebutEvenement(date);

        NewEvenementRequest request = new NewEvenementRequest(frise, theme, evenement, selectedEvenementIndex);

        FriseDisplayRepository repo = FakeDependencyInjection.getFriseDisplayRepository();

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.clear();
        compositeDisposable.add(repo.createNewEvenement(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Frise>(){

                    @Override
                    public void onSuccess(@NonNull Frise responseFrise) {
                        Log.i("UPDATING EVENEMENT", "Ca s'est bien passé !");
                        frise = responseFrise;
                        setupRecyclerview();
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Evènement mis à jour", Toast.LENGTH_SHORT)
                                .show();
                        setResult(FICHES_UPDATED);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("CREATING FRISE ERROR", e.toString());
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Erreur lors de la mise à jour de l'évènement", Toast.LENGTH_SHORT)
                                .show();
                    }
                }));
    }
}