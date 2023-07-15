package com.mindbriks.sparkle.utils.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.Profile;

public class LikesViewHolder extends RecyclerView.ViewHolder {
    public TextView mUserName, mUserDistance;
    public String mUserId;
    public ImageView mUserImage;

    public LikesViewHolder(View itemView) {
        super(itemView);

        mUserName = (TextView) itemView.findViewById(R.id.item_name);
        mUserDistance = (TextView) itemView.findViewById(R.id.item_distance);
        mUserImage = (ImageView) itemView.findViewById(R.id.item_image);
    }
}
