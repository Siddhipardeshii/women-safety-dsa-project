package com.example.simplewomensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// Activity for the second step of registration, where users add emergency contacts
public class RegistrationStepTwoActivity extends AppCompatActivity {

    // Declaring views for displaying and adding contacts
    private LinearLayout contactContainer;
    private Button btnAddContact, btnNextTerms;
    // A list to hold arrays of EditTexts (Name, Relation, Phone for each contact)
    private ArrayList<EditText[]> contactInputs = new ArrayList<>();
    private DatabaseHelper dbHelper; // Helper class to handle database operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step_two);

        // Initializing views
        contactContainer = findViewById(R.id.contactContainer);
        btnAddContact = findViewById(R.id.btnAddContact);
        btnNextTerms = findViewById(R.id.btnNextTerms);
        dbHelper = new DatabaseHelper(this); // Initialize the database helper

        // Retrieving the data passed from the previous activity
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String phone = intent.getStringExtra("phone");
        String address = intent.getStringExtra("address");
        String age = intent.getStringExtra("age");
        String password = intent.getStringExtra("password");

        // Initially adding one emergency contact input field
        addContactField();

        // Button to add another contact input field
        btnAddContact.setOnClickListener(v -> addContactField());

        // Button to proceed to the next step (Terms and Conditions)
        btnNextTerms.setOnClickListener(v -> {
            ArrayList<String> contactList = new ArrayList<>();

            // Looping through each added contact field and extracting data
            for (EditText[] inputs : contactInputs) {
                String cname = inputs[0].getText().toString().trim(); // Contact name
                String crelation = inputs[1].getText().toString().trim(); // Relation to the user
                String cphone = inputs[2].getText().toString().trim(); // Contact phone number

                // If all fields are filled, add the contact to the database and the contact list
                if (!cname.isEmpty() && !crelation.isEmpty() && !cphone.isEmpty()) {
                    dbHelper.addContact(cname, crelation, cphone); // Store the contact in the database
                    contactList.add(cname + "," + crelation + "," + cphone); // Add the contact info to the list
                }
            }

            // If no contact was added, show a toast message and prevent proceeding
            if (contactList.size() == 0) {
                Toast.makeText(this, "Please enter at least one emergency contact", Toast.LENGTH_SHORT).show();
                return;
            }

            // Prepare an intent to pass the data to the next activity (RegistrationTermsActivity)
            Intent nextIntent = new Intent(RegistrationStepTwoActivity.this, RegistrationTermsActivity.class);
            nextIntent.putExtra("userId", userId); // Passing user info
            nextIntent.putExtra("name", name);
            nextIntent.putExtra("email", email);
            nextIntent.putExtra("phone", phone);
            nextIntent.putExtra("address", address);
            nextIntent.putExtra("age", age);
            nextIntent.putExtra("password", password);
            nextIntent.putStringArrayListExtra("contacts", contactList); // Passing contacts as an ArrayList

            // Start the next activity
            startActivity(nextIntent);
        });
    }

    // Method to add a new set of fields for an emergency contact
    private void addContactField() {
        // Creating EditText fields for name, relation, and phone number
        EditText etName = new EditText(this);
        etName.setHint("Contact Name"); // Hint for name field
        contactContainer.addView(etName); // Adding the name field to the container

        EditText etRelation = new EditText(this);
        etRelation.setHint("Relation"); // Hint for relation field
        contactContainer.addView(etRelation); // Adding the relation field to the container

        EditText etPhone = new EditText(this);
        etPhone.setHint("Contact Phone"); // Hint for phone field
        etPhone.setInputType(android.text.InputType.TYPE_CLASS_PHONE); // Ensuring the input type is for phone numbers
        contactContainer.addView(etPhone); // Adding the phone field to the container

        // Storing the three fields as an array (for easier management of each contact input group)
        contactInputs.add(new EditText[]{etName, etRelation, etPhone});
    }
}
