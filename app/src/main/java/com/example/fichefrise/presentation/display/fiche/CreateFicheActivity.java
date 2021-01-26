package com.example.fichefrise.presentation.display.fiche;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fichefrise.R;
import com.example.fichefrise.presentation.display.fiche.fragment.CreateFicheFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateFicheActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fiche);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Création d'une fiche");

        setupViewPagerAndTabs();

        FloatingActionButton fab = findViewById(R.id.fabCreateFiche);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateFicheActivity.this, FichesListActivity.class);
                startActivity(i);
            }
        });

    }

    private void setupViewPagerAndTabs() {

        viewPager = findViewById(R.id.view_pager);
        final CreateFicheFragment fragmentRecto = CreateFicheFragment.newInstance(0);
        final CreateFicheFragment fragmentVerso = CreateFicheFragment.newInstance(1);
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
                    return fragmentRecto.name;
                }
                return fragmentVerso.name;
            }
        });
    }
}