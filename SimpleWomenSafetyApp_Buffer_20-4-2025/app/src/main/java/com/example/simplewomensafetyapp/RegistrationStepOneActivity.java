package com.example.simplewomensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

// Activity for the first step of the registration process
public class RegistrationStepOneActivity extends AppCompatActivity {

    // Declaring EditText views and Button for user input
    EditText etName, etEmail, etPhone, etAddress, etAge, etPassword;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step_one);

        // Initializing EditText views and Button from the layout
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etAge = findViewById(R.id.etAge);
        etPassword = findViewById(R.id.etPassword);
        btnNext = findViewById(R.id.btnNextStep);

        // Button click listener for moving to the next step in registration
        btnNext.setOnClickListener(v -> {
            // Retrieving input values from the EditText fields and trimming any extra spaces
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validation: Check if any field is empty, and display a Toast message if so
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || age.isEmpty() || password.isEmpty()) {
                // Show Toast if validation fails
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return; // Prevent further action if validation fails
            }

            // Generating a random 4-digit user ID using Random class
            String userId = String.format("%04d", new Random().nextInt(10000)); // Random number between 0 and 9999, padded to 4 digits

            // Creating an Intent to transition to the next activity (RegistrationStepTwoActivity)
            Intent intent = new Intent(RegistrationStepOneActivity.this, RegistrationStepTwoActivity.class);

            // Passing the collected data to the next step using intent extras
            intent.putExtra("userId", userId);  // Pass user ID
            intent.putExtra("name", name);  // Pass name
            intent.putExtra("email", email);  // Pass email
            intent.putExtra("phone", phone);  // Pass phone number
            intent.putExtra("address", address);  // Pass address
            intent.putExtra("age", age);  // Pass age
            intent.putExtra("password", password);  // Pass password

            // Start the next activity (RegistrationStepTwoActivity) with the provided data
            startActivity(intent);
        });
    }
}
