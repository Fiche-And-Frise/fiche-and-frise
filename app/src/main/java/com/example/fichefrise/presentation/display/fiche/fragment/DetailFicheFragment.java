package com.example.fichefrise.presentation.display.fiche.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fichefrise.R;

public class DetailFicheFragment extends Fragment {

    private View view;
    public String name = "";
    private String content;

    public void setName(String name) {
        this.name = name;
    }

    public DetailFicheFragment(int type, String content){
        this.content = content;
        if(type == 0){
            setName("RECTO");
        }else{
            setName("VERSO");
        }
    }

    public static DetailFicheFragment newInstance(int type, String content) {
        return new DetailFicheFragment(type, content);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail_fiche, container, false);
        TextView rectoTextView = view.findViewById(R.id.textViewFragment);
        rectoTextView.setText(content);
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
