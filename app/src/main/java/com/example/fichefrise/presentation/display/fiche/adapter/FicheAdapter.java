package com.example.fichefrise.presentation.display.fiche.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Fiche;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.presentation.display.fiche.mapper.FicheToViewModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FicheAdapter extends RecyclerView.Adapter<FicheAdapter.FicheViewHolder> {

    private List<Fiche> viewItemList = Collections.emptyList();
    private List<Theme> themesList = Collections.emptyList();
    private FicheActionInterface ficheActionInterface;

    public FicheAdapter(FicheActionInterface ficheActionInterface){
        this.ficheActionInterface = ficheActionInterface;
    }

    @NonNull
    @Override
    public FicheViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fiche_recyclerview, parent, false);
        return new FicheViewHolder(v, this.ficheActionInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FicheViewHolder holder, int position) {
        holder.bind(viewItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.viewItemList.size();
    }

    public void bindFicheViewModelList(List<Theme> allThemes) {
        this.viewItemList = new ArrayList<>();
        Log.i("DANS LE BIND", "all themes : " + allThemes.size());
        FicheToViewModelMapper mapper = new FicheToViewModelMapper();
        for(Theme t : allThemes){
            for(Fiche f : t.getListFiches()){
                f.setThemeId(t.getThemeId());
                f.setColor(t.getColor());
                this.viewItemList.add(f);
            }
        }
        Collections.sort(viewItemList, Fiche.comparator);
        notifyDataSetChanged();
    }

    public static class FicheViewHolder extends RecyclerView.ViewHolder {

        private TextView ficheNameTextView;
        private View v;
        private Fiche ficheViewItem;
        private ImageView icon;
        private FicheActionInterface ficheActionInterface;

        public FicheViewHolder(@NonNull View itemView, final FicheActionInterface ficheActionInterface) {
            super(itemView);
            this.v = itemView;
            this.ficheActionInterface = ficheActionInterface;
            this.ficheNameTextView =itemView.findViewById(R.id.ficheNameTextView);
            this.icon = itemView.findViewById(R.id.fiche_icon);
            this.ficheNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ficheActionInterface.onFicheClicked(ficheViewItem);
                }
            });
        }

        public void bind(Fiche fiche){
            this.icon.setColorFilter(fiche.getColor());
            this.ficheViewItem = fiche;
            this.ficheNameTextView.setText(ficheViewItem.getNomFiche());
        }
    }
}
