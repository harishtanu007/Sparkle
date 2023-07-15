package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.Interest;
import com.mindbriks.sparkle.utils.viewholder.InterestsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsViewHolder> {
    private List<Interest> interestList;
    private Context context;

    private int maxSelection = 5;

    private List<Interest> selectedInterests;

    public InterestsAdapter(List<Interest> interestList, Context context) {
        this.interestList = interestList;
        this.context = context;
        this.selectedInterests = new ArrayList<>();
    }

    @Override
    public InterestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        InterestsViewHolder interestsViewHolder = new InterestsViewHolder(layoutView);
        return interestsViewHolder;
    }

    @Override
    public void onBindViewHolder(InterestsViewHolder holder, int position) {
        //Set ViewTag
        holder.itemView.setTag(position);
        Interest interest = interestList.get(position);
        holder.name.setText(interest.getName());
        holder.name.setOnClickListener(v -> {
            if (selectedInterests.contains(interest)) {
                selectedInterests.remove(interest);
                setDisabledButton(holder);
            } else {
                if (selectedInterests.size() < maxSelection) {
                    selectedInterests.add(interest);
                    setEnabledButton(holder);
                } else {
                    Toast.makeText(context, "Maximum selection reached", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void setEnabledButton(InterestsViewHolder holder) {
        holder.name.setBackground(context.getResources().getDrawable(R.drawable.enabled_button));
        holder.name.setTextColor(context.getResources().getColor(R.color.button_color));
    }

    void setDisabledButton(InterestsViewHolder holder) {
        holder.name.setBackground(context.getResources().getDrawable(R.drawable.disabled_button));
        holder.name.setTextColor(context.getResources().getColor(R.color.black));
    }

    @Override
    public int getItemCount() {
        return this.interestList.size();
    }

    public List<Interest> getSelectedInterests(){
        return selectedInterests;
    }
}
