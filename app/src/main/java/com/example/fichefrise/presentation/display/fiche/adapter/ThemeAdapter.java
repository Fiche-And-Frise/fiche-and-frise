package com.example.fichefrise.presentation.display.fiche.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.presentation.display.fiche.mapper.FicheToViewModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>{
    private List<Theme> themeList = Collections.emptyList();
    private FicheActionInterface ficheActionInterface;

    public ThemeAdapter(FicheActionInterface ficheActionInterface){
        this.ficheActionInterface = ficheActionInterface;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_theme_recyclerview, parent, false);
        return new ThemeViewHolder(v, this.ficheActionInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        holder.bind(themeList.get(position), themeList.get(position).getListFiches().size() > 0);
    }

    @Override
    public int getItemCount() {
        return this.themeList.size();
    }

    public void bindThemeViewModelList(List<Theme> themeListStatic) {
        this.themeList = themeListStatic;
        Collections.sort(themeList, Theme.comparator);
        notifyDataSetChanged();
    }

    public static class ThemeViewHolder extends RecyclerView.ViewHolder {

        private TextView themeNameTextView;
        private RecyclerView ficheRecyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private FicheAdapter ficheAdapter;
        private View v;
        private Theme theme;
        private FicheActionInterface ficheActionInterface;

        public ThemeViewHolder(@NonNull View itemView, FicheActionInterface ficheActionInterface) {
            super(itemView);
            this.v = itemView;
            this.ficheActionInterface = ficheActionInterface;
            this.themeNameTextView =itemView.findViewById(R.id.theme_textView);
        }

        public void bind(Theme item, Boolean visible){
            if(!visible){
                this.themeNameTextView.setVisibility(View.GONE);
                if(ficheRecyclerView != null){
                    ficheRecyclerView.setVisibility(View.GONE);
                }
                return;
            }
            this.theme = item;
            this.themeNameTextView.setText(theme.getNomTheme());
            ficheRecyclerView = v.findViewById(R.id.fiches_recyclerview);
            layoutManager = new LinearLayoutManager(v.getContext());
            ficheAdapter = new FicheAdapter(this.ficheActionInterface);
            this.themeNameTextView.setVisibility(View.VISIBLE);
            ficheRecyclerView.setVisibility(View.VISIBLE);

            FicheToViewModelMapper mapper = new FicheToViewModelMapper();

            ficheAdapter.bindFicheViewModelList(Collections.singletonList(theme));
            ficheRecyclerView.setAdapter(this.ficheAdapter);
            ficheRecyclerView.setLayoutManager(this.layoutManager);
        }
    }
}
