package com.example.simplewomensafetyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACTS = "contacts";

    // Table columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_RELATION = "relation";
    private static final String COLUMN_PHONE = "phone";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CONTACTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_RELATION + " TEXT, " +
                COLUMN_PHONE + " TEXT)";
        db.execSQL(CREATE_TABLE); // Execute the query to create the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate the table if there's a database upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db); // Recreate the table
    }

    /**
     * Add a new contact to the database.
     *
     * @param name     The name of the contact.
     * @param relation The relation of the contact (optional).
     * @param phone    The phone number of the contact.
     */
    public void addContact(String name, String relation, String phone) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database

        // Insert contact into the table using ContentValues
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_RELATION, relation);
        values.put(COLUMN_PHONE, phone);

        db.insert(TABLE_CONTACTS, null, values); // Insert data into the contacts table
        db.close(); // Close the database connection
    }

    /**
     * Get all stored contacts' phone numbers from the database.
     *
     * @param context The context used to create the database helper.
     * @return A list of phone numbers stored in the database.
     */
    public static ArrayList<String> getStoredContacts(Context context) {
        ArrayList<String> contacts = new ArrayList<>();
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase(); // Get readable database

        // Query to fetch phone numbers from the database
        Cursor cursor = db.rawQuery("SELECT phone FROM " + TABLE_CONTACTS, null);

        // Iterate through all records
        if (cursor.moveToFirst()) {
            do {
                // Add each phone number to the contacts list
                contacts.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close(); // Close the cursor after usage
        db.close(); // Close the database connection
        return contacts; // Return the list of phone numbers
    }
}
