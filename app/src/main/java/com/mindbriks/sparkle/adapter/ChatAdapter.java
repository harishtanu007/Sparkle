package com.mindbriks.sparkle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.model.ChatMessage;

import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessage> messageList;
    private Context context;
    private String currentUserID;

    private final int MESSAGE_TYPE_SENT = 1;
    private final int MESSAGE_TYPE_RECEIVED = 2;

    public ChatAdapter(Context context, List<ChatMessage> messageList, String currentUserID) {
        this.context = context;
        this.messageList = messageList;
        this.currentUserID = currentUserID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_TYPE_SENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_TYPE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case MESSAGE_TYPE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messageList.get(position);
        if (message.getSenderID().equals(currentUserID)) {
            return MESSAGE_TYPE_SENT;
        } else {
            return MESSAGE_TYPE_RECEIVED;
        }
    }

    public void add(String message) {
        messageList.add(new ChatMessage(message, true, new Date(), "2"));
    }

    private static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_view_message_sent);
            timeText = itemView.findViewById(R.id.text_view_time_sent);
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessageText());
            timeText.setText("10:40 PM");
        }
    }

    private static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message_received_text);
            timeText = itemView.findViewById(R.id.message_received_time);
            //nameText = itemView.findViewById(R.id.text_message_name);
            profileImage = itemView.findViewById(R.id.message_received_profile_image);
        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessageText());
            timeText.setText(message.getMessageTime());
            // nameText.setText(message.getSenderName());
            Glide.with(itemView.getContext())
                    .load(R.drawable.card_view_place_holder_image)
                    .into(profileImage);
        }
    }
}
