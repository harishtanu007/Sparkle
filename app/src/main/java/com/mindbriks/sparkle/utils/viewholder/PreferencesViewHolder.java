package com.mindbriks.sparkle.utils.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;

public class PreferencesViewHolder extends RecyclerView.ViewHolder {
    public TextView mOptionText;

    public PreferencesViewHolder(View itemView) {
        super(itemView);
        mOptionText = itemView.findViewById(R.id.option_text);
    }
}
