package com.mindbriks.sparkle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mindbriks.sparkle.adapter.ChatAdapter;
import com.mindbriks.sparkle.model.ChatMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;

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

        // Set up the RecyclerView
        mRecyclerView = findViewById(R.id.message_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ChatAdapter(getApplicationContext(), getMessages(), "2");
        mRecyclerView.setAdapter(mAdapter);
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
}