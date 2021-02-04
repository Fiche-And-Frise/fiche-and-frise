package com.example.fichefrise.presentation.display.fiche;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;
import com.example.fichefrise.data.repository.FicheDisplayRepository;
import com.example.fichefrise.presentation.display.fiche.fragment.DetailFicheFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailFicheActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Fiche fiche;
    private Theme theme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiche_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.fiche = (Fiche) getIntent().getSerializableExtra("fiche");
        this.theme = (Theme) getIntent().getSerializableExtra("theme");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Aperçu d'une fiche");

        setupTextViews();
        setupViewPagerAndTabs();
        setupDeleteFab();

    }

    private void setupTextViews() {
        TextView titleView = findViewById(R.id.fiche_name);
        titleView.setText(fiche.getNomFiche());
        TextView themeView = findViewById(R.id.theme_name);
        themeView.setText(theme.getNomTheme());
    }

    private void setupViewPagerAndTabs() {
        viewPager = findViewById(R.id.view_pager);
        final DetailFicheFragment fragmentRecto = DetailFicheFragment.newInstance(0, fiche.getRecto());
        final DetailFicheFragment fragmentVerso = DetailFicheFragment.newInstance(1, fiche.getVerso());
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

    private void setupDeleteFab(){
        FloatingActionButton fab = findViewById(R.id.fabDeleteFiche);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FicheDisplayRepository repo = FakeDependencyInjection.getFicheDisplayRepository();
                CompositeDisposable compositeDisposable = new CompositeDisposable();
                compositeDisposable.clear();
                compositeDisposable.add(repo.deleteFiche(fiche.getFicheId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver(){

                            @Override
                            public void onComplete() {
                                setResult(FichesListActivity.FICHES_UPDATED);
                                finish();
                                Toast.makeText(FakeDependencyInjection.getApplicationContext(), "Fiche supprimée", Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("ERROR", e.toString());
                            }
                        }));
            }
        });
    }
}
