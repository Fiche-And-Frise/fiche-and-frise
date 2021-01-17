package com.example.fichefrise.presentation.display.fiche.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fichefrise.R;

public class FicheFragment extends Fragment {

    private View view;
    public String name = "";

    public void setName(String name) {
        this.name = name;
    }

    public FicheFragment(int type){
        if(type == 0){
            setName("RECTO");
        }else{
            setName("VERSO");
        }
    }

    public static FicheFragment newInstance(int type) {
        return new FicheFragment(type);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fiche, container, false);
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
}
