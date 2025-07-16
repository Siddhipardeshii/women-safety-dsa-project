package com.example.simplewomensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simplewomensafetyapp.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationTermsActivity extends AppCompatActivity {

    Button btnRegister; // Declaring the Register button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_terms); // Setting the content view

        btnRegister = findViewById(R.id.btnRegister); // Initializing the Register button

        // Set an OnClickListener for the Register button
        btnRegister.setOnClickListener(v -> {
            // Retrieve user data passed from the previous activities
            String userId = getIntent().getStringExtra("userId");
            String name = getIntent().getStringExtra("name");
            String email = getIntent().getStringExtra("email");
            String phone = getIntent().getStringExtra("phone");
            String address = getIntent().getStringExtra("address");
            String age = getIntent().getStringExtra("age");
            String password = getIntent().getStringExtra("password");

            // 🔽 Initialize Firebase Database reference for users node
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

            // 🔽 Create a HashMap to hold the user's data
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("userId", userId);
            userMap.put("name", name);
            userMap.put("email", email);
            userMap.put("phone", phone);
            userMap.put("address", address);
            userMap.put("age", age);
            userMap.put("password", password);

            // 🔽 Saving the user's data to Firebase using the phone number as a unique key
            usersRef.child(phone).setValue(userMap)
                    .addOnSuccessListener(aVoid -> {
                        // Successful registration
                        Log.d("Registration", "User successfully registered.");
                        Toast.makeText(this, "Registration successful. Please log in.", Toast.LENGTH_LONG).show();
                        // Redirect to LoginActivity after successful registration
                        startActivity(new Intent(this, LoginActivity.class));
                        finish(); // Close the current activity
                    })
                    .addOnFailureListener(e -> {
                        // If there's an error, log the error and show a Toast message
                        Log.e("RegistrationError", "Error during registration: " + e.getMessage());
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
