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
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImportEvenementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImportEvenementFragment extends Fragment{

    private AutoCompleteTextView acTextView;
    private TextInputEditText dateInput;
    private List<Fiche> fiches;
    private List<String> fichesNames = new ArrayList<>();
    private Fiche selectedFiche = null;

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
        dateInput = view.findViewById(R.id.evenement_date_edittext);
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
        ArrayAdapter<Fiche> adapter =
                new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, fiches);
        acTextView.setAdapter(adapter);
        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                selectedFiche = (Fiche) parent.getItemAtPosition(pos);
                //your stuff
            }
        });

    }

    public String getName(){
        return "Importer";
    }

    public Fiche getSelectedFiche(){
        return this.selectedFiche;
    }

    public String getEvenementDate(){
        return Objects.requireNonNull(dateInput.getText()).toString();
    }

}