package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.UserProfileActivity;
import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.utils.DobHelper;

import java.util.ArrayList;
import java.util.List;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private final Context context;
    private List<DbUser> profileList;

    public ProfileAdapter(@NonNull Context context) {
        this.context = context;
        this.profileList = new ArrayList<>();
    }

    public void setMyDataList(List<DbUser> profileList) {
        this.profileList = profileList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.card_view_profile, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DbUser profile = profileList.get(position);
        if (profile != null) {
            holder.cardViewName.setText(profile.getName() + ", " + DobHelper.calculateAge(profile.getDob()));
            holder.cardViewDistance.setText(profile.getHeight() + " " + getDistanceMetric());
            String imageUrl = profile.getProfile_image();

            if (imageUrl == null || imageUrl.isEmpty()) {
                // If the image value is null, load a default placeholder image
                Glide.with(context).load(R.drawable.card_view_place_holder_image).into(holder.cardViewImage);
            } else {
                // If the image value is not null, load the actual image using Glide
                Glide.with(context).load(imageUrl).into(holder.cardViewImage);
            }

            holder.cardViewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), UserProfileActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    private String getDistanceMetric() {
        return "miles";
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }


    @Override
    public int getItemCount() {
        if (profileList == null) return 0;
        return profileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardViewName, cardViewDistance;
        ImageView cardViewImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewImage = itemView.findViewById(R.id.item_image);
            cardViewName = itemView.findViewById(R.id.item_name);
            cardViewDistance = itemView.findViewById(R.id.item_distance);

        }
    }
}
