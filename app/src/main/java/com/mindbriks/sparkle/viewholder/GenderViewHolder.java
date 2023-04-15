package com.mindbriks.sparkle.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.Gender;

public class GenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mGenderText;

    public GenderViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mGenderText = itemView.findViewById(R.id.gender_text);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), mGenderText.getText(), Toast.LENGTH_LONG);
    }
}
