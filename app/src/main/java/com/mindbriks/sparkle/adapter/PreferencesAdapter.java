package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.viewholder.PreferencesViewHolder;

import java.util.List;

public class PreferencesAdapter<T extends Object> extends RecyclerView.Adapter<PreferencesViewHolder> {
    private List<T> options;

    private Context context;

    private int selectedItem = -1;

    public PreferencesAdapter(List<T> options, Context context) {
        this.options = options;
        this.context = context;
    }

    @Override
    public PreferencesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gender_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        PreferencesViewHolder preferencesViewHolder = new PreferencesViewHolder(layoutView);
        return preferencesViewHolder;
    }

    @Override
    public void onBindViewHolder(PreferencesViewHolder holder, int position) {
        //Set ViewTag
        holder.itemView.setTag(position);
        holder.mOptionText.setText(options.get(position).toString());

        if (position == selectedItem) {
            setEnabledButton(holder);
        } else {
            setDisabledButton(holder);
        }

        holder.mOptionText.setOnClickListener(v -> {
            setSelectedItem(position);
        });
    }

    void setEnabledButton(PreferencesViewHolder holder) {
        holder.mOptionText.setBackground(context.getResources().getDrawable(R.drawable.enabled_button));
        holder.mOptionText.setTextColor(context.getResources().getColor(R.color.button_color));
    }

    void setDisabledButton(PreferencesViewHolder holder) {
        holder.mOptionText.setBackground(context.getResources().getDrawable(R.drawable.disabled_button));
        holder.mOptionText.setTextColor(context.getResources().getColor(R.color.black));
    }

    @Override
    public int getItemCount() {
        return this.options.size();
    }

    public T getSelectedItem() {
        if (selectedItem >= 0 && selectedItem < options.size())
            return options.get(selectedItem);
        return null;
    }

    private void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }
}
