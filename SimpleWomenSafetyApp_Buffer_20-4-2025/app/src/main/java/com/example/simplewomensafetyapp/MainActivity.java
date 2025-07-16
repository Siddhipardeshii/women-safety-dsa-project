package com.example.simplewomensafetyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnRegisterPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        if (prefs.contains("userId")) {
            // If logged in, navigate to the dashboard
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
            return;
        }

        // Set content view
        setContentView(R.layout.activity_main);

        // Initialize buttons
        btnLogin = findViewById(R.id.btnLoginPage);
        btnRegisterPage = findViewById(R.id.btnRegisterPage);

        // On Login button click, navigate to LoginActivity
        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class))
        );

        // On Register button click, navigate to RegistrationStepOneActivity
        btnRegisterPage.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RegistrationStepOneActivity.class))
        );
    }
}
