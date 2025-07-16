package com.example.simplewomensafetyapp;

/**
 * Model class representing a contact saved for emergency purposes.
 */
public class ContactModel {
    private String name;
    private String relation; // Optional: e.g., "Friend", "Sister", or leave blank
    private String phone;

    /**
     * Default constructor required for Firebase deserialization.
     */
    public ContactModel() {
        // Needed for Firebase
    }

    /**
     * Constructs a new ContactModel with given details.
     *
     * @param name     Name of the contact.
     * @param relation Relationship to the user (can be blank).
     * @param phone    Phone number of the contact.
     */
    public ContactModel(String name, String relation, String phone) {
        this.name = name;
        this.relation = relation;
        this.phone = phone;
    }

    /**
     * Gets the contact's name.
     *
     * @return Name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the contact's relationship to the user.
     *
     * @return Relationship label (can be blank).
     */
    public String getRelation() {
        return relation;
    }

    /**
     * Gets the contact's phone number.
     *
     * @return Phone number.
     */
    public String getPhone() {
        return phone;
    }
}
