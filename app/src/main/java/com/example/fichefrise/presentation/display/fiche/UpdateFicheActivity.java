package com.example.fichefrise.presentation.display.fiche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.NewFicheRequest;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.fragment.CreateFicheFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UpdateFicheActivity extends AppCompatActivity {


    private EditText newFicheNameEditText;
    private TextView themeTextView;
    private CreateFicheFragment fragmentRecto, fragmentVerso;
    private Theme theme;
    private Fiche fiche;

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_fiche);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Modification d'une fiche");

        fiche = (Fiche) getIntent().getSerializableExtra("fiche");
        theme = (Theme) getIntent().getSerializableExtra("theme");

        setupViewPagerAndTabs();
        setupViewWithData();

        FloatingActionButton fab = findViewById(R.id.fabUpdateFiche);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(CreateFicheActivity.this, FichesListActivity.class);
                //startActivity(i);
                updateFiche();
            }
        });

    }

    private void setupViewWithData() {
        newFicheNameEditText = findViewById(R.id.update_fiche_name);
        themeTextView = findViewById(R.id.theme_text_view);
        newFicheNameEditText.setText(fiche.getNomFiche());
        themeTextView.setText(theme.getNomTheme());
    }

    private void setupViewPagerAndTabs() {

        viewPager = findViewById(R.id.view_pager);
        fragmentRecto = CreateFicheFragment.newInstance(0, fiche.getRecto());
        fragmentVerso = CreateFicheFragment.newInstance(1, fiche.getVerso());
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
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateFiche(){
        Log.i("UPDATING FICHE", "Beginning");
        String ficheName = newFicheNameEditText.getText().toString();
        if(ficheName.length() < 1){
            Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Veuillez entrer un nom de fiche", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        String ficheRecto = fragmentRecto.getContent();
        String ficheVerso = fragmentVerso.getContent();
        fiche.setNomFiche(ficheName);
        fiche.setRecto(ficheRecto);
        fiche.setVerso(ficheVerso);

        FicheDisplayRepository repo = FakeDependencyInjection.getFicheDisplayRepository();
        NewFicheRequest request = new NewFicheRequest(fiche, theme);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.clear();
        compositeDisposable.add(repo.updateFiche(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Fiche>(){

                    @Override
                    public void onSuccess(@NonNull Fiche fiche) {
                        Log.i("CREATING FICHE", "Ca s'est bien passé !");
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("fiche", fiche);
                        resultIntent.putExtra("theme", theme);
                        setResult(FichesListActivity.FICHE_UPDATED, resultIntent);
                        finish();
                        Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Fiche mise à jour", Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("UPDATING FICHE ERROR", e.toString());
                    }
                }));

    }
}