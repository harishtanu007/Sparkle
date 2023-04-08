package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.EditProfileActivity;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.ProfileItem;
import com.mindbriks.sparkle.viewholder.ProfileViewHolder;

import java.util.List;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileViewHolder> {
    private List<ProfileItem> profileItems;
    private Context context;

    public ProfileListAdapter(List<ProfileItem> profileItems, Context context) {
        this.profileItems = profileItems;
        this.context = context;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ProfileViewHolder profileViewHolder = new ProfileViewHolder(layoutView);
        return profileViewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        //Set ViewTag
        holder.itemView.setTag(position);
        ProfileItem profileItem = profileItems.get(position);
        holder.mTitle.setText(profileItem.getTitle());
        holder.mValue.setText(profileItem.getValue());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditProfileActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.profileItems.size();
    }
}
