package com.example.fichefrise.presentation.display.frise.adapter;

import android.os.SystemClock;
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
import com.example.fichefrise.data.api.model.Frise;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.presentation.display.fiche.adapter.FicheActionInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FriseAdapter extends RecyclerView.Adapter<FriseAdapter.FriseViewHolder> {

    private List<Frise> viewItemList = Collections.emptyList();
    private final List<Theme> themesList = Collections.emptyList();
    private final FriseActionInterface friseActionInterface;

    public FriseAdapter(FriseActionInterface friseActionInterface){
            this.friseActionInterface = friseActionInterface;
    }

    @NonNull
    @Override
    public FriseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_frise_recyclerview, parent, false);
            return new FriseViewHolder(v, this.friseActionInterface);
            }

    @Override
    public void onBindViewHolder(@NonNull FriseViewHolder holder, int position) {
            holder.bind(viewItemList.get(position));
    }

    @Override
    public int getItemCount() {
            return this.viewItemList.size();
    }

    public void bindFriseViewModelList(List<Theme> allThemes) {
            this.viewItemList = new ArrayList<>();
            Log.i("DANS LE BIND", "all themes : " + allThemes.size());
            for(Theme t : allThemes){
                for(Frise f : t.getListFrises()){
                    f.setCurrentTheme(t.getThemeId());
                    f.setColor(t.getColor());
                    this.viewItemList.add(f);
                }
            }
            Collections.sort(viewItemList, Frise.comparator);
            notifyDataSetChanged();
    }

    public static class FriseViewHolder extends RecyclerView.ViewHolder {

        private final TextView friseNameTextView;
        private Frise friseViewItem;
        private final ImageView icon;
        private long mLastClickTime = 0;

        public FriseViewHolder(@NonNull View itemView, final FriseActionInterface friseActionInterface) {
            super(itemView);
            this.friseNameTextView = itemView.findViewById(R.id.friseNameTextView);
            this.icon = itemView.findViewById(R.id.frise_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // mis-clicking prevention, using threshold of 1000 ms
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    friseActionInterface.onFriseClicked(friseViewItem);
                }
            });
        }

        public void bind(Frise frise){
            this.friseViewItem = frise;
            this.friseNameTextView.setText(friseViewItem.getNomFrise());
            this.icon.setColorFilter(frise.getColor());
        }
    }
}
