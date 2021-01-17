package com.example.fichefrise.presentation.display.fiche;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fichefrise.R;
import com.example.fichefrise.presentation.display.fiche.fragment.FicheFragment;

public class DetailFicheActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiche_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Aperçu d'une fiche");

        setupViewPagerAndTabs();

    }

    private void setupViewPagerAndTabs() {
        viewPager = findViewById(R.id.view_pager);
        final FicheFragment fragmentRecto = FicheFragment.newInstance(0);
        final FicheFragment fragmentVerso = FicheFragment.newInstance(1);
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
