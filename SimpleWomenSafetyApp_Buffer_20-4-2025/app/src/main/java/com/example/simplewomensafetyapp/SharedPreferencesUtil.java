package com.example.simplewomensafetyapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String PREF_NAME = "UserSession"; // Name of the preferences file
    private static final String KEY_USER_ID = "userId";  // Key to store the userId

    // Save the userId in SharedPreferences
    public static void saveUserId(Context context, String userId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_USER_ID, userId).apply(); // Save userId
    }

    // Check if the user is logged in by verifying if userId exists in SharedPreferences
    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.contains(KEY_USER_ID);  // Check if the userId is present
    }

    // Clear user session (remove userId from SharedPreferences)
    public static void clearUserSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();  // Remove all data in the preferences
    }
}
