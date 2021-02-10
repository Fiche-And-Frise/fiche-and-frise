package com.example.fichefrise.presentation.display.frise.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Evenement;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImportEvenementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImportEvenementFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private List<Evenement> evenements;

    public ImportEvenementFragment() {
        // Required empty public constructor
    }

    public ImportEvenementFragment(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public static ImportEvenementFragment newInstance(List<Evenement> evenements) {
        ImportEvenementFragment fragment = new ImportEvenementFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_import_evenement, container, false);
    }

    public String getName(){
        return "IMPORT";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}