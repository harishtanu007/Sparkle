package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.UserProfileActivity;
import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.utils.DistanceHelper;
import com.mindbriks.sparkle.utils.DobHelper;
import com.mindbriks.sparkle.utils.viewholder.LikesViewHolder;

import java.util.ArrayList;
import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesViewHolder> {
    private final Context context;
    private List<DbUser> profileList;
    private DbUser currentUser;

    public LikesAdapter(@NonNull Context context, @NonNull DbUser currentUser) {
        this.context = context;
        this.profileList = new ArrayList<>();
        this.currentUser = currentUser;
    }

    public void setMyDataList(List<DbUser> profileList) {
        this.profileList = profileList;
        notifyDataSetChanged();
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
        final DbUser profile = profileList.get(position);
        if (profile != null) {
            holder.mUserName.setText(profile.getName() + ", " + DobHelper.calculateAge(profile.getDob()));
            holder.mUserDistance.setText((int) Math.round(DistanceHelper.getDistance(currentUser.getLocation(), profile.getLocation())) + " " + getDistanceMetric());
            String imageUrl = profile.getProfile_image();
            if (imageUrl == null || imageUrl.isEmpty()) {
                // If the image value is null, load a default placeholder image
                Glide.with(context).load(R.drawable.card_view_place_holder_image).into(holder.mUserImage);
            } else {
                // If the image value is not null, load the actual image using Glide
                Glide.with(context).load(imageUrl).into(holder.mUserImage);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), UserProfileActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (profileList == null) return 0;
        return profileList.size();
    }

    private String getDistanceMetric() {
        return "miles";
    }
}
