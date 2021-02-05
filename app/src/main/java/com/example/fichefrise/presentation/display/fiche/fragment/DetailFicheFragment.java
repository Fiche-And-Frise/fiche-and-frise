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
    private String name = "";
    private String content;
    private TextView contentView;

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
        contentView = view.findViewById(R.id.textViewFragment);
        contentView.setText(content);
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

    public String getName(){
        return this.name;
    }

    public void setContent(String content){
        this.content = content;
        this.contentView.setText(content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
