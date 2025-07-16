package com.example.simplewomensafetyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adapter class for displaying chat messages between user and bot.
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> chatList;

    // Constants to identify the type of message
    private static final int VIEW_TYPE_USER = 1;
    private static final int VIEW_TYPE_BOT = 2;

    /**
     * Constructor to initialize chat list.
     */
    public ChatAdapter(List<ChatMessage> chatList) {
        this.chatList = chatList;
    }

    /**
     * Determines the view type based on who sent the message.
     */
    @Override
    public int getItemViewType(int position) {
        return chatList.get(position).isUser() ? VIEW_TYPE_USER : VIEW_TYPE_BOT;
    }

    /**
     * Inflates the appropriate layout based on the message type.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_USER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_message, parent, false);
            return new UserMessageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bot_message, parent, false);
            return new BotMessageViewHolder(view);
        }
    }

    /**
     * Binds the message data to the appropriate ViewHolder.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = chatList.get(position);
        if (holder instanceof UserMessageViewHolder) {
            ((UserMessageViewHolder) holder).userTextView.setText(message.getMessage());
        } else {
            ((BotMessageViewHolder) holder).botTextView.setText(message.getMessage());
        }
    }

    /**
     * Returns the total number of chat messages.
     */
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /**
     * ViewHolder class for user messages.
     */
    static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView;

        UserMessageViewHolder(View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.userMessage);
        }
    }

    /**
     * ViewHolder class for bot messages.
     */
    static class BotMessageViewHolder extends RecyclerView.ViewHolder {
        TextView botTextView;

        BotMessageViewHolder(View itemView) {
            super(itemView);
            botTextView = itemView.findViewById(R.id.botMessage);
        }
    }
}
