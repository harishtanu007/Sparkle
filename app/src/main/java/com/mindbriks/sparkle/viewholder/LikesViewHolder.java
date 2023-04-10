package com.mindbriks.sparkle.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.Profile;

public class LikesViewHolder extends RecyclerView.ViewHolder{
    public TextView mUserName, mUserDistance;
    public String mUserId;
    public ImageView mUserImage;
    public LikesViewHolder(View itemView) {
        super(itemView);

        mUserName = (TextView) itemView.findViewById(R.id.item_name);
        mUserDistance = (TextView) itemView.findViewById(R.id.item_distance);
        mUserImage = (ImageView) itemView.findViewById(R.id.item_image);
    }

    public void setPostImage(Profile profile, Context context) {
        mUserId = profile.getId();

        String imageUrl = profile.getProfilePic();

        if (imageUrl == null || imageUrl.isEmpty()) {
            // If the image value is null, load a default placeholder image
            Glide.with(context)
                    .load(R.drawable.card_view_place_holder_image)
                    .into(mUserImage);
        } else {
            // If the image value is not null, load the actual image using Glide
            Glide.with(context)
                    .load(imageUrl)
                    .into(mUserImage);
        }
    }
}
