package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.Gender;
import com.mindbriks.sparkle.viewholder.GenderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class GenderAdapter extends RecyclerView.Adapter<GenderViewHolder> {
    private List<Gender> genders;

    private Context context;

    private List<Gender> selectedGenders = new ArrayList<>();

    public GenderAdapter(List<Gender> genders, Context context) {
        this.genders = genders;
        this.context = context;
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
        Gender gender = genders.get(position);
        holder.mGenderText.setText(gender.getName());
        holder.itemView.setOnClickListener(v -> {
            if (!gender.isSelected()) {
                gender.setSelected(!gender.isSelected());
                selectedGenders.add(gender);
                holder.mGenderText.setBackground(context.getResources().getDrawable(R.drawable.enabled_button));
                holder.mGenderText.setTextColor(context.getResources().getColor(R.color.button_color));
            } else {
                gender.setSelected(!gender.isSelected());
                selectedGenders.remove(gender);
                holder.mGenderText.setBackground(context.getResources().getDrawable(R.drawable.disabled_button));
                holder.mGenderText.setTextColor(context.getResources().getColor(R.color.black));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.genders.size();
    }
}
