package com.example.fichefrise.presentation.display.fiche.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fichefrise.R;

public class CreateFicheFragment extends Fragment {

    private View view;
    private String name = "";
    EditText editText;


    public void setName(String name) {
        this.name = name;
    }

    public CreateFicheFragment(int type){
        if(type == 0){
            setName("RECTO");
        }else{
            setName("VERSO");
        }
    }

    public static CreateFicheFragment newInstance(int type) {
        return new CreateFicheFragment(type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_fiche, container, false);
        editText = view.findViewById(R.id.editTextFragment);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public String getName(){
        return this.name;
    }

    public String getContent(){
        return this.editText.getText().toString();
    }
}
