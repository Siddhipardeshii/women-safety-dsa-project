package com.example.simplewomensafetyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhone, etPassword;
    private Button btnLogin;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Initialize Firebase Database reference
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Check if user is already logged in
        checkIfAlreadyLoggedIn();

        btnLogin.setOnClickListener(v -> {
            String userId = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Debugging logs (remove in production)
            Log.d("LOGIN", "Phone: " + userId + ", Password: " + password);

            // Validate input fields
            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter phone and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Authenticate user
            authenticateUser(userId, password);
        });
    }

    private void checkIfAlreadyLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String savedUserId = sharedPreferences.getString("userId", null);

        if (savedUserId != null) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }
    }

    private void authenticateUser(String userId, String password) {
        // Firebase database query for user
        usersRef.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DataSnapshot snapshot = task.getResult();
                String savedPassword = snapshot.child("password").getValue(String.class);

                // Check if password matches
                if (savedPassword != null && savedPassword.equals(password)) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtil.saveUserId(this, userId);
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "User not found. Redirecting to registration...", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, RegistrationStepOneActivity.class));
            }
        }).addOnFailureListener(e -> {
            // Handle Firebase errors (network issues, etc.)
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}
