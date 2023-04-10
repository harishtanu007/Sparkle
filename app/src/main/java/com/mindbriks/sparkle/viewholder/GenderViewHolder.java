package com.mindbriks.sparkle.viewholder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;

public class GenderViewHolder extends RecyclerView.ViewHolder {
    public TextView mGenderText;

    public GenderViewHolder(View itemView) {
        super(itemView);

        mGenderText = itemView.findViewById(R.id.gender_text);
    }
}
