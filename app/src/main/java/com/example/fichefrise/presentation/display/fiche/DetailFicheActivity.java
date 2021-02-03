package com.example.fichefrise.presentation.display.fiche;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.presentation.display.fiche.fragment.DetailFicheFragment;

public class DetailFicheActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Fiche fiche;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiche_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.fiche = (Fiche) getIntent().getSerializableExtra("fiche");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Aperçu d'une fiche");

        setupTextViews();
        setupViewPagerAndTabs();

    }

    private void setupTextViews() {
        TextView titleView = findViewById(R.id.fiche_name);
        titleView.setText(fiche.getNomFiche());
        TextView themeView = findViewById(R.id.theme_name);
        themeView.setText(fiche.getThemeName());
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
                    return fragmentRecto.name;
                }
                return fragmentVerso.name;
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
}
