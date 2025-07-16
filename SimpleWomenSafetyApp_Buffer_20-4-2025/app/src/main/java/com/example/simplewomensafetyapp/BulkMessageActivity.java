package com.example.simplewomensafetyapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BulkMessageActivity extends AppCompatActivity {

    // UI Elements
    private Button sendAlertButton;

    // Location service client
    private FusedLocationProviderClient fusedLocationClient;

    // Permission request code
    private static final int REQUEST_CODE = 1;

    // Firebase Realtime Database reference (future use, e.g., for logging alerts)
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference usersRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulk_message);

        // Initialize views and services
        sendAlertButton = findViewById(R.id.btnSendBulk);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request necessary permissions
        requestPermissionsIfNeeded();

        // Set click listener on "Send Alert" button
        sendAlertButton.setOnClickListener(v -> sendEmergencySMS());
    }

    /**
     * Request SMS and location permissions if not already granted.
     */
    private void requestPermissionsIfNeeded() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, REQUEST_CODE);
        }
    }

    /**
     * Get last known location and send emergency SMS with that location.
     */
    private void sendEmergencySMS() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    // Base emergency alert message
                    String alertMessage = "🚨 I am in danger. Please help me!";
                    String locationMessage;

                    // Append location link if available
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        locationMessage = alertMessage + "\n📍 My location: https://maps.google.com/?q=" + latitude + "," + longitude;
                    } else {
                        locationMessage = alertMessage + "\n📍 Location not available!";
                    }

                    // Send message to all emergency contacts
                    sendSMS(locationMessage);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(BulkMessageActivity.this, "Failed to get location", Toast.LENGTH_SHORT).show()
                );
    }

    /**
     * Send an SMS message to all stored emergency contacts.
     *
     * @param message The message to be sent.
     */
    private void sendSMS(String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();

            // Get stored emergency contacts
            ArrayList<String> emergencyContacts = DatabaseHelper.getStoredContacts(BulkMessageActivity.this);

            if (emergencyContacts == null || emergencyContacts.isEmpty()) {
                Toast.makeText(this, "No emergency contacts found!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send SMS to each contact
            for (String contact : emergencyContacts) {
                smsManager.sendTextMessage(contact, null, message, null, null);
            }

            Toast.makeText(this, "✅ Alert sent with location!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "❌ SMS failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Handle permission result from user prompt.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            Toast.makeText(this, allGranted ? "Permissions granted" : "Permissions not granted", Toast.LENGTH_SHORT).show();
        }
    }
}
