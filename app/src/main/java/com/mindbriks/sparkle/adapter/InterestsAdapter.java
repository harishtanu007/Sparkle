package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.Interest;
import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.viewholder.InterestsViewHolder;
import com.mindbriks.sparkle.viewholder.LikesViewHolder;

import java.util.List;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsViewHolder> {
    private List<Interest> interestList;
    private Context context;

    public InterestsAdapter(List<Interest> interestList, Context context) {
        this.interestList = interestList;
        this.context = context;
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
        holder.itemView.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return this.interestList.size();
    }
}
