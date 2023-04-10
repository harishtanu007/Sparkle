package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.viewholder.LikesViewHolder;

import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesViewHolder> {
    private List<Profile> likesList;
    private Context context;

    public LikesAdapter(List<Profile> likesList, Context context) {
        this.likesList = likesList;
        this.context = context;
    }

    @Override
    public LikesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_card, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        LikesViewHolder likesViewHolder = new LikesViewHolder(layoutView);
        return likesViewHolder;
    }

    @Override
    public void onBindViewHolder(LikesViewHolder holder, int position) {
        //Set ViewTag
        holder.itemView.setTag(position);
        Profile profile = likesList.get(position);
        holder.mUserName.setText(profile.getName() + ", " + profile.getAge());
        holder.mUserDistance.setText(profile.getDistance() + " " + getDistanceMetric());
        holder.setPostImage(profile, holder.itemView.getContext());
        holder.itemView.setOnClickListener(v -> {
        });
    }

    private String getDistanceMetric() {
        return "miles";
    }

    @Override
    public int getItemCount() {
        return this.likesList.size();
    }
}
