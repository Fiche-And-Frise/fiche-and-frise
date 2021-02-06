package com.example.fichefrise.presentation.display.frise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fichefrise.R;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheActionInterface;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheAdapter;
import com.example.fichefrise.presentation.display.fiche.mapper.FicheToViewModelMapper;

import java.util.Collections;
import java.util.List;

public class ThemeFriseAdapter extends RecyclerView.Adapter<ThemeFriseAdapter.ThemeViewHolder>{
    private List<Theme> themeList = Collections.emptyList();
    private FriseActionInterface friseActionInterface;

    public ThemeFriseAdapter(FriseActionInterface friseActionInterface){
        this.friseActionInterface = friseActionInterface;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_theme_frise_recyclerview, parent, false);
        return new ThemeViewHolder(v, this.friseActionInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        holder.bind(themeList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.themeList.size();
    }

    public void bindFriseViewModelList(List<Theme> themeListStatic) {
        this.themeList = themeListStatic;
        notifyDataSetChanged();
    }

    public static class ThemeViewHolder extends RecyclerView.ViewHolder {

        private TextView themeNameTextView;
        private RecyclerView friseRecyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private FriseAdapter friseAdapter;
        private View v;
        private Theme theme;
        private FriseActionInterface friseActionInterface;

        public ThemeViewHolder(@NonNull View itemView, FriseActionInterface friseActionInterface) {
            super(itemView);
            this.v = itemView;
            this.friseActionInterface = friseActionInterface;
            this.themeNameTextView =itemView.findViewById(R.id.theme_textView);
        }

        public void bind(Theme item){
            this.theme = item;
            this.themeNameTextView.setText(theme.getNomTheme());
            friseRecyclerView = v.findViewById(R.id.frises_recyclerview);
            layoutManager = new LinearLayoutManager(v.getContext());
            friseAdapter = new FriseAdapter(this.friseActionInterface);

            //FicheToViewModelMapper mapper = new FicheToViewModelMapper();

            friseAdapter.bindFriseViewModelList(Collections.singletonList(theme));
            friseRecyclerView.setAdapter(this.friseAdapter);
            friseRecyclerView.setLayoutManager(this.layoutManager);
        }
    }
}
