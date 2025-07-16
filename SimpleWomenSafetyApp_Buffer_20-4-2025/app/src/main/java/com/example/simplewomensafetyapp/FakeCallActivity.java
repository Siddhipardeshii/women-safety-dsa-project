package com.example.simplewomensafetyapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class FakeCallActivity extends AppCompatActivity {

    private MediaPlayer player;
    private int secondsElapsed = 0;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private TextView callTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call);

        // Randomly select a caller
        String[] callers = {"Mummy", "Papa", "Appa", "Samruuu", "Arya", "Dive Teacher"};
        String caller = callers[new Random().nextInt(callers.length)];

        // UI elements
        TextView callerName = findViewById(R.id.txtCallerName);
        TextView callerNumber = findViewById(R.id.txtCallerNumber);
        callTimer = findViewById(R.id.txtTimer);
        ImageView profileImage = findViewById(R.id.imgProfile);
        ImageView iconImage = findViewById(R.id.imgIcons);
        Button btnAnswer = findViewById(R.id.btnAnswer);
        Button btnDecline = findViewById(R.id.btnDecline);

        // Set the caller details
        callerName.setText(caller);
        callerNumber.setText("+91 9876543210 | India");
        profileImage.setImageResource(R.drawable.profile);
        iconImage.setImageResource(R.drawable.dial3);
        iconImage.setVisibility(ImageView.INVISIBLE); // Hide icon initially

        // Hide the call timer initially
        callTimer.setText("00:00");
        callTimer.setVisibility(TextView.GONE);

        // Play ringtone
        playRingtone();

        // Answer button click listener
        btnAnswer.setOnClickListener(v -> {
            stopRingtone();
            iconImage.setVisibility(ImageView.VISIBLE); // Show the call icon
            callTimer.setVisibility(TextView.VISIBLE); // Show the call timer
            centerDeclineButton(btnDecline, btnAnswer);
            startCallTimer(); // Start the timer
        });

        // Decline button click listener
        btnDecline.setOnClickListener(v -> {
            stopRingtone(); // Stop ringtone if declined
            Intent intent = new Intent(FakeCallActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // Close current activity
        });
    }

    // Method to play ringtone
    private void playRingtone() {
        player = MediaPlayer.create(this, R.raw.ringtone);
        player.setLooping(true); // Loop the ringtone
        player.start();
    }

    // Method to stop ringtone
    private void stopRingtone() {
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release(); // Release resources
        }
    }

    // Method to start the call timer
    private void startCallTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                secondsElapsed++;
                int minutes = secondsElapsed / 60;
                int seconds = secondsElapsed % 60;
                updateCallTimer(minutes, seconds);
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.post(timerRunnable);
    }

    // Method to update the timer UI
    private void updateCallTimer(int minutes, int seconds) {
        callTimer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    // Method to center the decline button and hide the answer button
    private void centerDeclineButton(Button btnDecline, Button btnAnswer) {
        btnAnswer.setVisibility(Button.GONE);

        LinearLayout parentLayout = (LinearLayout) btnDecline.getParent();
        parentLayout.removeAllViews(); // Remove existing views
        parentLayout.setGravity(Gravity.CENTER); // Center the content

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);
        btnDecline.setLayoutParams(params);

        parentLayout.addView(btnDecline); // Add only decline button back to the layout
    }

    @Override
    protected void onDestroy() {
        stopRingtone(); // Ensure the ringtone is stopped
        timerHandler.removeCallbacks(timerRunnable); // Remove any pending timers
        super.onDestroy();
    }
}
