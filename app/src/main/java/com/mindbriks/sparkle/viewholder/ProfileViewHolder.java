package com.mindbriks.sparkle.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;

public class ProfileViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitle, mValue;

    public ProfileViewHolder(View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.title_text_view);
        mValue = (TextView) itemView.findViewById(R.id.value_text_view);
    }

}
