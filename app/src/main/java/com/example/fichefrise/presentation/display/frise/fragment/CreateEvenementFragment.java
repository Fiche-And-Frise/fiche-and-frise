package com.example.fichefrise.presentation.display.frise.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Evenement;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CreateEvenementFragment extends Fragment{

    private TextInputEditText nameInput, dateInput;
    public CreateEvenementFragment() {
        // Required empty public constructor
    }

    public static CreateEvenementFragment newInstance() {
        return new CreateEvenementFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_evenement, container, false);
        nameInput = view.findViewById(R.id.evenement_name_edittext);
        dateInput = view.findViewById(R.id.evenement_date_edittext);

        return view;
    }

    public String getName(){
        return "Créer";
    }

    public String getEvenementDate(){
        return  dateInput.getText().toString();
    }

    public String getEvenementName(){
        return nameInput.getText().toString();
    }
}