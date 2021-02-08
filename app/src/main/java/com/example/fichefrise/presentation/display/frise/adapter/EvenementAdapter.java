package com.example.fichefrise.presentation.display.frise.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Evenement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EvenementAdapter extends RecyclerView.Adapter<EvenementAdapter.EvenementViewHolder>{

    private List<Evenement> viewItemList = Collections.emptyList();
    private final static int RANDOM_EVENT = 999, LAST_EVENT = 995;

    @NonNull
    @Override
    public EvenementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(viewType == LAST_EVENT){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.last_item_evenement_recyclerview, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_evenement_recyclerview, parent, false);
        }
        return new EvenementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EvenementAdapter.EvenementViewHolder holder, int position) {
        holder.bind(viewItemList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount()-1){
            return LAST_EVENT;
        }
        return RANDOM_EVENT;
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    public void bindViewModelList(List<Evenement> evenements){
        this.viewItemList = new ArrayList<>();
        this.viewItemList = evenements;
        this.viewItemList.add(null);
        notifyDataSetChanged();
    }

    public static class EvenementViewHolder extends RecyclerView.ViewHolder{

        private TextView dateTextView, nameTextView;
        private View view;
        private Evenement evenementViewItem;

        public EvenementViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            dateTextView = view.findViewById(R.id.date_evenement_textview);
            nameTextView = view.findViewById(R.id.name_evenement_textview);
        }

        public void bind(Evenement evenement){
            if(evenement != null){
                evenementViewItem = evenement;
                dateTextView.setText(String.valueOf(evenement.getDateDebutEvenement()));
                nameTextView.setText(evenement.getNomEvenement());
            }
        }
    }
}
