package com.example.simplewomensafetyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class HelpCenterDetailActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center_detail);

        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String phone = getIntent().getStringExtra("phone");
        double lat = getIntent().getDoubleExtra("latitude", 0);
        double lng = getIntent().getDoubleExtra("longitude", 0);
        double userLat = getIntent().getDoubleExtra("userLat", 0);
        double userLng = getIntent().getDoubleExtra("userLng", 0);

        // Calculate distance if coordinates are valid
        float[] result = new float[1];
        if (lat != 0 && lng != 0 && userLat != 0 && userLng != 0) {
            Location.distanceBetween(userLat, userLng, lat, lng, result);
            float distanceInMeters = result[0];
            float distanceInKm = distanceInMeters / 1000;

            // Set distance text
            TextView distView = findViewById(R.id.tvHelpCenterDistance);
            distView.setText(String.format("Distance: %.2f km", distanceInKm));
        }

        // Set up TextViews and Buttons
        TextView nameView = findViewById(R.id.tvHelpCenterName);
        TextView addrView = findViewById(R.id.tvHelpCenterAddress);
        TextView phoneView = findViewById(R.id.tvHelpCenterContact);
        Button callBtn = findViewById(R.id.btnCall);
        Button smsBtn = findViewById(R.id.btnSendSMS);

        nameView.setText(name);
        addrView.setText(address);
        phoneView.setText(phone);

        // Call button listener
        callBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            } else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
            }
        });

        // SMS button listener
        smsBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, "Hi, I need help. Please assist.", null, null);
                Toast.makeText(HelpCenterDetailActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
            }
        });
    }

    // Handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, retry the action (call or SMS)
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
