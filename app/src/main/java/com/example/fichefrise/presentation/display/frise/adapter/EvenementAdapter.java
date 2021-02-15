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
import com.example.fichefrise.data.api.model.Evenement;
import com.example.fichefrise.data.api.model.Theme;
import com.example.fichefrise.data.di.FakeDependencyInjection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EvenementAdapter extends RecyclerView.Adapter<EvenementAdapter.EvenementViewHolder>{

    private final int endDate;
    private List<Evenement> viewItemList = Collections.emptyList();
    private final static int RANDOM_EVENT = 999, LAST_EVENT = 995;
    private EvenementActionInterface evenementActionInterface;

    public EvenementAdapter(EvenementActionInterface evenementActionInterface, int endDate){
        this.evenementActionInterface = evenementActionInterface;
        this.endDate = endDate;
    }

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
        return new EvenementViewHolder(v, this.evenementActionInterface, endDate);
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
        List<Theme> allThemes = FakeDependencyInjection.getAllThemes();
        for(Evenement e : evenements){
            if(e.getThemeId() != -1){
                for(Theme t : allThemes){
                    if(t.getThemeId() == e.getThemeId()){
                        e.setColor(t.getColor());
                        break;
                    }
                }
            }
            viewItemList.add(e);
        }
        this.viewItemList.add(null);
        notifyDataSetChanged();
    }

    public static class EvenementViewHolder extends RecyclerView.ViewHolder{

        private final TextView dateTextView, nameTextView, endDateTextView;
        private final int endDate;
        private View view;
        private Evenement evenementViewItem;
        private EvenementActionInterface evenementActionInterface;
        private long mLastClickTime = 0;

        public EvenementViewHolder(@NonNull View itemView, EvenementActionInterface evenementActionInterface, int endDate) {
            super(itemView);
            view = itemView;
            this.endDate = endDate;
            dateTextView = view.findViewById(R.id.date_evenement_textview);
            nameTextView = view.findViewById(R.id.name_evenement_textview);
            endDateTextView = view.findViewById(R.id.end_date_textview);
            dateTextView.setOnClickListener( v ->{
                    // mis-clicking prevention, using threshold of 1000 ms
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            evenementActionInterface.onEvenementClicked(evenementViewItem);
            });
            nameTextView.setOnClickListener( v -> {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                evenementActionInterface.onEvenementClicked(evenementViewItem);
            });
        }

        public void bind(Evenement evenement){
            if(evenement != null){
                evenementViewItem = evenement;
                dateTextView.setText(evenement.getDateDebutEvenement());
                nameTextView.setText(evenement.getNomEvenement());
                if(evenement.getColor() != 0) {
                    nameTextView.setTextColor(evenement.getColor());
                }

            }
            if(endDateTextView != null){
                endDateTextView.setText(String.valueOf(endDate));
            }
        }
    }
}
