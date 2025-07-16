package com.example.simplewomensafetyapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatBot Activity - Handles basic chatbot interface and logic
 * for answering questions related to the Women Safety App.
 */
public class ChatBot extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText questionInput;
    private Button sendButton;
    private ChatAdapter chatAdapter;
    private final List<ChatMessage> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot);

        // Initialize UI components
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        questionInput = findViewById(R.id.questionInput);
        sendButton = findViewById(R.id.sendButton);

        // Set up RecyclerView with adapter and layout manager
        chatAdapter = new ChatAdapter(chatList);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        // Handle send button click
        sendButton.setOnClickListener(v -> {
            String userInput = questionInput.getText().toString().trim().toLowerCase();

            // Proceed only if user input is not empty
            if (!TextUtils.isEmpty(userInput)) {
                addMessage(userInput, true);                      // Add user message
                addMessage(getBotResponse(userInput), false);     // Add bot response
                questionInput.setText("");                        // Clear input field
            }
        });
    }

    /**
     * Adds a message to the chat and updates the view.
     *
     * @param text The message text
     * @param isUser true if user message, false if bot message
     */
    private void addMessage(String text, boolean isUser) {
        chatList.add(new ChatMessage(text, isUser));
        chatAdapter.notifyItemInserted(chatList.size() - 1);
        chatRecyclerView.scrollToPosition(chatList.size() - 1);
    }

    /**
     * Generates a bot response based on the user input.
     *
     * @param input The user's message
     * @return A relevant bot reply
     */
    private String getBotResponse(String input) {
        switch (input) {
            case "hello":
            case "hi":
                return "Hello! I'm your safety assistant. Ask anything about the app.";
            case "help":
                return "Ask about SOS, Fake Call, Bulk Message, Signs, or Map.";
            case "bye":
                return "Goodbye! Stay safe.";
            case "fake call":
                return "This simulates a call after 5 seconds to help you escape.";
            case "map":
                return "The Map button shows your location and nearby help centers.";
            case "signs":
                return "It helps you recognize unsafe signs or behaviors.";
            case "bulk message":
                return "Sends alert messages to multiple contacts.";
            case "sos":
                return "Sends an SOS alert with your location to emergency contacts.";
            case "dashboard":
                return "The Dashboard links to all features: SOS, Map, Fake Call, etc.";
            default:
                return "Sorry, I didn’t get that. Try asking about SOS, Map, Fake Call, or help.";
        }
    }
}
