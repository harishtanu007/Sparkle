package com.mindbriks.sparkle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mindbriks.sparkle.adapter.ChatAdapter;
import com.mindbriks.sparkle.model.ChatMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        ImageView mUserImage = (ImageView) toolbar.findViewById(R.id.profile_image);
        String imageUrl = "";

        if (imageUrl.isEmpty()) {
            // If the image value is null, load a default placeholder image
            Glide.with(getApplicationContext())
                    .load(R.drawable.card_view_place_holder_image)
                    .into(mUserImage);
        } else {
            // If the image value is not null, load the actual image using Glide
            Glide.with(getApplicationContext())
                    .load(imageUrl)
                    .into(mUserImage);
        }

        // Set up the RecyclerView
        mRecyclerView = findViewById(R.id.message_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ChatAdapter mAdapter = new ChatAdapter(getApplicationContext(), getMessages(), "2");
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
            }
        });

        ImageView mSendMessageButton = findViewById(R.id.send_message_button);
        EditText messageText = findViewById(R.id.message_input);
        messageText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
                } else {
                }
            }
        });
        messageText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mSendMessageButton.setBackground(getResources().getDrawable(R.drawable.rounded_corner_enabled_button));
                    mSendMessageButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    mSendMessageButton.setBackground(getResources().getDrawable(R.drawable.rounded_corner_disabled_button));
                    mSendMessageButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.send_button_tint), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });

        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                if (message.trim().length() > 0) {
                    mAdapter.add(message);
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                    messageText.setText("");
                }
            }
        });
    }

    private List<ChatMessage> getMessages() {
        // TODO: Replace with your own data source
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("Hi there!", true, new Date(), "1"));
        messages.add(new ChatMessage("Hey, how's it going?", false, new Date(), "2"));
        messages.add(new ChatMessage("Not bad, thanks. How about you?", true, new Date(), "2"));
        messages.add(new ChatMessage("I'm doing pretty well, thanks.", false, new Date(), "1"));
        messages.add(new ChatMessage("Cool.", true, new Date(), "2"));
        messages.add(new ChatMessage("So, what do you want to talk about?", false, new Date(), "1"));
        messages.add(new ChatMessage("I'm not sure. How about movies?", true, new Date(), "2"));
        messages.add(new ChatMessage("Sure, I love movies! What's your favorite?", false, new Date(), "1"));
        messages.add(new ChatMessage("I really like The Godfather. How about you?", true, new Date(), "2"));
        messages.add(new ChatMessage("Oh, I've never seen that one. I'm more into action movies.", false, new Date(), "1"));
        messages.add(new ChatMessage("Really? You should definitely check it out sometime.", true, new Date(), "2"));
        messages.add(new ChatMessage("Maybe I will. Thanks for the recommendation.", false, new Date(), "1"));
        return messages;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}