package com.example.simplewomensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * DashboardActivity class handles the main user interface of the app,
 * providing access to various features such as Map, Bulk Message, Fake Call, Signs, and Chatbot.
 */
public class DashboardActivity extends AppCompatActivity {

    Button btnMap, btnBulkMessage, btnLogout, fakeCallBtn, btnSigns, btnChatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize buttons for dashboard actions
        btnMap = findViewById(R.id.btnDashboardMap);
        btnBulkMessage = findViewById(R.id.btnDashboardBulkMessage);
        btnLogout = findViewById(R.id.btnDashboardLogout);
        fakeCallBtn = findViewById(R.id.fakeCallButton);
        btnSigns = findViewById(R.id.btnDashboardSigns);
        btnChatbot = findViewById(R.id.btnChatbot); // Chatbot button

        // Open Map activity on button click
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        // Open Bulk Message activity on button click
        btnBulkMessage.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, BulkMessageActivity.class);
            startActivity(intent);
        });

        // Open Signs activity on button click
        btnSigns.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, Sign.class);
            startActivity(intent);
        });

        // Fake Call - Trigger after a 5-second delay to simulate an escape opportunity
        fakeCallBtn.setOnClickListener(v -> new Handler().postDelayed(() -> {
            Intent intent = new Intent(DashboardActivity.this, FakeCallActivity.class);
            startActivity(intent);
        }, 5000));

        // Logout and redirect to MainActivity (Login screen)
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(DashboardActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            // Clear activity stack and start a new task for login screen
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Finish current activity to prevent going back to it
        });

        // Open ChatBot activity on button click
        btnChatbot.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ChatBot.class);
            startActivity(intent);
        });
    }
}
