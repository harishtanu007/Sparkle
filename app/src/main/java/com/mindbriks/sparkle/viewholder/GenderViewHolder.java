package com.mindbriks.sparkle.viewholder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.R;

public class GenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mGenderText;
    public GenderViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mGenderText = itemView.findViewById(R.id.gender_text);
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(view.getContext(), ChatActivity.class);
//        Bundle b = new Bundle();
//        b.putString("matchId", mMatchId.getText().toString());
//        intent.putExtras(b);
//        view.getContext().startActivity(intent);
        Toast.makeText(view.getContext(), mGenderText.getText(), Toast.LENGTH_LONG);
    }
}
