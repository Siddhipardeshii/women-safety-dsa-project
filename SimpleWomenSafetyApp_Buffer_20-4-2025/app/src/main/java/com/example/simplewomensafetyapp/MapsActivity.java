package com.example.simplewomensafetyapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnSuccessListener;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

// Main Activity that handles the map interaction and help center display
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap; // Google Map object to display the map
    private LatLng userLocation; // User's current location
    private List<HelpCenter> helpCenters = new ArrayList<>(); // List to store help center information

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001; // Request code for location permission

    private FusedLocationProviderClient fusedLocationClient; // FusedLocationProviderClient to get user location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize the fused location client to fetch location data
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Get the map fragment and set the callback for when the map is ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Load the help centers data from assets (JSON file)
        loadHelpCenters();
    }

    // Load help centers information from a JSON file stored in assets folder
    private void loadHelpCenters() {
        try {
            InputStream is = getAssets().open("help_center.json"); // Open help_center.json from assets
            Reader reader = new InputStreamReader(is); // Reader to read the JSON file
            StringBuilder stringBuilder = new StringBuilder();
            int data = reader.read();
            while (data != -1) {
                char current = (char) data;
                stringBuilder.append(current); // Read the file content
                data = reader.read();
            }

            // Parse JSON content
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("help_centers");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject helpCenterJson = jsonArray.getJSONObject(i);
                HelpCenter helpCenter = new HelpCenter(
                        helpCenterJson.getString("name"),
                        helpCenterJson.getString("address"),
                        helpCenterJson.getString("phone"),
                        helpCenterJson.getDouble("latitude"),
                        helpCenterJson.getDouble("longitude")
                );
                helpCenters.add(helpCenter); // Add help center to the list
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error if any exception occurs
            Toast.makeText(this, "Error loading help centers", Toast.LENGTH_SHORT).show();
        }
    }

    // When the map is ready, this method is triggered to set up the map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Check if location permission is granted and enable location if so
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    // UPDATED METHOD: Calculates distance between two LatLng points using the Haversine formula
    private double distanceBetween(LatLng l1, LatLng l2) {
        double R = 6371000; // Earth radius in meters
        double lat1Rad = Math.toRadians(l1.latitude); // Latitude of point 1 in radians
        double lat2Rad = Math.toRadians(l2.latitude); // Latitude of point 2 in radians
        double deltaLat = Math.toRadians(l2.latitude - l1.latitude); // Latitude difference in radians
        double deltaLng = Math.toRadians(l2.longitude - l1.longitude); // Longitude difference in radians

        // Haversine formula to calculate the distance
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Return distance in meters
    }

    // Adds markers to the map for the nearest help centers to the user
    private void addNearestHelpCenterMarkers() {
        if (userLocation == null) return; // If user location is not available, exit

        // Sort help centers by proximity to the user's location
        if (userLocation != null && helpCenters != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                helpCenters.sort((h1, h2) -> {
                    double d1 = distanceBetween(userLocation, new LatLng(h1.getLatitude(), h1.getLongitude()));
                    double d2 = distanceBetween(userLocation, new LatLng(h2.getLatitude(), h2.getLongitude()));
                    return Double.compare(d1, d2); // Sort by distance
                });
            }
        }

        // Add markers for the nearest help centers (up to 5)
        for (int i = 0; i < Math.min(5, helpCenters.size()); i++) {
            HelpCenter hc = helpCenters.get(i);
            LatLng loc = new LatLng(hc.getLatitude(), hc.getLongitude());

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .title(hc.getName()) // Set the title of the marker
                    .snippet("Tap for contact") // Additional information in the snippet
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))); // Marker color

            // Set the tag to pass the help center object when the marker is clicked
            marker.setTag(hc);
        }

        // Set an info window click listener to open help center details
        mMap.setOnInfoWindowClickListener(marker -> {
            HelpCenter selected = (HelpCenter) marker.getTag(); // Get the HelpCenter object from the marker tag
            if (selected != null) {
                // Start HelpCenterDetailActivity to show more info about the selected help center
                Intent intent = new Intent(MapsActivity.this, HelpCenterDetailActivity.class);
                intent.putExtra("name", selected.getName());
                intent.putExtra("address", selected.getAddress());
                intent.putExtra("phone", selected.getPhone());
                intent.putExtra("latitude", selected.getLatitude());
                intent.putExtra("longitude", selected.getLongitude());
                intent.putExtra("userLat", userLocation.latitude);
                intent.putExtra("userLng", userLocation.longitude);
                startActivity(intent);
            }
        });
    }

    // Enable user's location on the map
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        mMap.setMyLocationEnabled(true); // Show user's location on the map as a blue dot

        // Get the last known location of the user and update the map
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                userLocation = new LatLng(location.getLatitude(), location.getLongitude()); // Set user's location

                // Move camera to user's location with zoom
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14));

                // Add a marker at the user's location (blue color)
                mMap.addMarker(new MarkerOptions()
                        .position(userLocation)
                        .title("You are here")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))); // Blue marker

                addNearestHelpCenterMarkers(); // Add markers for the nearest help centers
            }
        });
    }

    // Handle location permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation(); // Permission granted, enable location
            } else {
                Toast.makeText(this, "Location permission is required to show your location", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
