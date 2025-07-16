package com.example.simplewomensafetyapp;

/**
 * Represents a single message in the chatbot conversation.
 * Stores the message text and the sender type (user or bot).
 */
public class ChatMessage {
    private String message;
    private boolean isUser;

    /**
     * Constructor to initialize a chat message.
     *
     * @param message The text of the message.
     * @param isUser  True if sent by user, false if sent by bot.
     */
    public ChatMessage(String message, boolean isUser) {
        this.message = message;
        this.isUser = isUser;
    }

    /**
     * Gets the message text.
     *
     * @return The chat message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Checks if the message was sent by the user.
     *
     * @return True if user message, false if bot.
     */
    public boolean isUser() {
        return isUser;
    }
}
