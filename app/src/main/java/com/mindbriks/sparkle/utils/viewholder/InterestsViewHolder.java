package com.mindbriks.sparkle.utils.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;

public class InterestsViewHolder extends RecyclerView.ViewHolder {
    public TextView name;

    public InterestsViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.interest_text);

    }
}
