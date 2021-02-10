package com.example.fichefrise.presentation.display.frise.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Fiche;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImportEvenementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImportEvenementFragment extends Fragment{

    private AutoCompleteTextView acTextView;
    private List<Fiche> fiches;
    private List<String> fichesNames = new ArrayList<>();

    public ImportEvenementFragment() {
        // Required empty public constructor
    }

    public ImportEvenementFragment(List<Fiche> allFiches) {
        this.fiches = allFiches;
    }

    public static ImportEvenementFragment newInstance(List<Fiche> allFiches) {
        return new ImportEvenementFragment(allFiches);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_import_evenement, container, false);
        acTextView = view.findViewById(R.id.evenement_auto_complete);
        setupAutoCompleteTextView();

        return view;
    }

    private void setupAutoCompleteTextView() {
        // Get the string array
        for(Fiche f : fiches){
            if (f != null)
                fichesNames.add(f.getNomFiche());
        }

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, fichesNames);
        acTextView.setAdapter(adapter);

    }

    public String getName(){
        return "IMPORT";
    }

}