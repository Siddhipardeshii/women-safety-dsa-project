package com.example.simplewomensafetyapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Sign extends AppCompatActivity {

    // Map to store emergency signs and their descriptions
    private Map<Integer, String> emergencySigns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);

        // Initialize the emergency signs map with images and descriptions
        initializeEmergencySigns();

        // Reference to the layout where emergency signs will be added
        LinearLayout emergencyLayout = findViewById(R.id.emergencySignsLayout);

        // Loop through emergency signs and add them to the layout
        for (Map.Entry<Integer, String> entry : emergencySigns.entrySet()) {
            addEmergencySign(emergencyLayout, entry.getKey(), entry.getValue());
        }

        // Add a header for the IPC laws section
        TextView lawsHeader = new TextView(this);
        lawsHeader.setText("Important Laws for Women's Safety");
        lawsHeader.setTextSize(18);
        lawsHeader.setPadding(0, 32, 0, 16);
        lawsHeader.setTypeface(null, android.graphics.Typeface.BOLD);
        emergencyLayout.addView(lawsHeader);

        // List of important laws related to women's safety
        String[] laws = {
                "• IPC Section 354 – Assault on a woman with intent to outrage her modesty.",
                "• IPC Section 354D – Stalking, physically or through electronic means.",
                "• IPC Section 509 – Words/gestures to insult a woman's modesty.",
                "• IPC Sections 375 & 376 – Defines and punishes rape.",
                "• Dowry Prohibition Act, 1961 – Prohibits giving or taking dowry.",
                "• Domestic Violence Act, 2005 – Protection from physical, mental, or verbal abuse.",
                "• Workplace Harassment Act, 2013 – Safeguards women at workplaces.",
                "• POCSO Act, 2012 – Protection of children from sexual offenses."
        };

        // Add each law to the layout
        for (String law : laws) {
            TextView lawText = new TextView(this);
            lawText.setText(law);
            lawText.setTextSize(15);
            lawText.setPadding(0, 8, 0, 0);
            emergencyLayout.addView(lawText);
        }
    }

    // Initialize the emergency signs map with images and descriptions
    private void initializeEmergencySigns() {
        emergencySigns = new LinkedHashMap<>(); // LinkedHashMap to maintain insertion order
        emergencySigns.put(R.drawable.thumb_in_palm, "Thumb tucked into palm then closed in a fist — a distress hand signal widely used to indicate silent help.");
        emergencySigns.put(R.drawable.safetysign_h, "Writing or showing 'SOS' on your palm or hand can silently alert others that you're in trouble.");
        emergencySigns.put(R.drawable.interact_eye, "Raising arms in a 'Y' shape — used to gain attention, especially from a distance.");
        emergencySigns.put(R.drawable.palm_to_palm, "Pressing palms together and pushing forward — a signal of needing help or expressing distress.");
        emergencySigns.put(R.drawable.waving_hand, "Waving one hand slowly — a discreet way to ask for attention or help without speaking.");
        emergencySigns.put(R.drawable.finger_lip, "Index finger over lips – Silent signal meaning you're in danger but can't speak.");
        emergencySigns.put(R.drawable.crossed_arm, "Crossed arms over chest – Used in rescue training to signal 'I need help.'");
    }

    // Method to add each emergency sign to the layout
    private void addEmergencySign(LinearLayout layout, int imageRes, String description) {
        // Create a row layout for each emergency sign with image and text
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setPadding(16, 16, 16, 16);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Create and set up the image view for the emergency sign
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageRes);
        imageView.setContentDescription(description); // Add content description for accessibility
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(400, 400);
        imageParams.setMargins(0, 0, 24, 0); // Margin between image and text
        imageView.setLayoutParams(imageParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Create and set up the text view for the emergency sign description
        TextView textView = new TextView(this);
        textView.setText(description);
        textView.setTextSize(16);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1 // Weight to fill remaining space
        ));

        // Add the image and text views to the row layout
        rowLayout.addView(imageView);
        rowLayout.addView(textView);

        // Add the row layout to the main layout
        layout.addView(rowLayout);
    }
}
