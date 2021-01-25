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
import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>{
    private List<Theme> themeList;

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_theme_recyclerview, parent, false);
        return new ThemeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        holder.bind(themeList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.themeList.size();
    }

    public void bindFicheViewModelList(List<Theme> themeListStatic) {
        this.themeList = themeListStatic;
        notifyDataSetChanged();
    }

    public static class ThemeViewHolder extends RecyclerView.ViewHolder {

        private TextView themeNameTextView;
        private RecyclerView ficheRecyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private FicheAdapter ficheAdapter;
        private View v;
        private Theme theme;

        public ThemeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.v = itemView;
            this.themeNameTextView =itemView.findViewById(R.id.theme_textView);
        }

        public void bind(Theme item){
            this.theme = item;
            this.themeNameTextView.setText(theme.getName());
            ficheRecyclerView = v.findViewById(R.id.fiches_recyclerview);
            layoutManager = new LinearLayoutManager(v.getContext());
            ficheAdapter = new FicheAdapter();

            FicheToViewModelMapper mapper = new FicheToViewModelMapper();

            ficheAdapter.bindFicheViewModelList(mapper.map(theme.getFiches()));
            ficheRecyclerView.setAdapter(this.ficheAdapter);
            ficheRecyclerView.setLayoutManager(this.layoutManager);
        }
    }
}
