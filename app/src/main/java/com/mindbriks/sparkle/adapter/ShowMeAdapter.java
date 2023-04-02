package com.mindbriks.sparkle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.viewholder.GenderViewHolder;

import java.util.List;

public class ShowMeAdapter extends RecyclerView.Adapter<GenderViewHolder> {
    private List<String> genders;

    public ShowMeAdapter(List<String> genders) {
        this.genders = genders;
    }

    @Override
    public GenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_me_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        GenderViewHolder genderViewHolder = new GenderViewHolder(layoutView);
        return genderViewHolder;
    }

    @Override
    public void onBindViewHolder(GenderViewHolder holder, int position) {
        //Set ViewTag
        holder.itemView.setTag(position);
        String gender = genders.get(position);
        holder.mGenderText.setText(gender);
        holder.itemView.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return this.genders.size();
    }
}
