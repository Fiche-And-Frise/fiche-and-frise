package com.example.fichefrise.presentation.display.fiche.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fichefrise.R;

import java.util.Collections;
import java.util.List;

public class FicheAdapter extends RecyclerView.Adapter<FicheAdapter.FicheViewHolder> {

    private List<FicheViewItem> viewItemList = Collections.emptyList();

    @NonNull
    @Override
    public FicheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fiche_recyclerview, parent, false);
        return new FicheViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FicheViewHolder holder, int position) {
        holder.bind(viewItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.viewItemList.size();
    }

    public void bindFicheViewModelList(List<FicheViewItem> fichesListStatic) {
        this.viewItemList = fichesListStatic;
        notifyDataSetChanged();
    }

    public static class FicheViewHolder extends RecyclerView.ViewHolder {

        private TextView ficheNameTextView;
        private View v;
        private FicheViewItem ficheViewItem;

        public FicheViewHolder(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;
            this.ficheNameTextView =itemView.findViewById(R.id.ficheNameTextView);
        }

        public void bind(FicheViewItem item){
            this.ficheViewItem = item;
            this.ficheNameTextView.setText(ficheViewItem.getNomFiche());
        }
    }
}
