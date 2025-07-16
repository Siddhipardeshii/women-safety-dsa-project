package com.example.simplewomensafetyapp;

import java.io.Serializable;

public class HelpCenter implements Serializable {
    private String name;
    private String address;
    private String phone;
    private double latitude;
    private double longitude;

    // Constructor with parameters
    public HelpCenter(String name, String address, String phone, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // No-argument constructor (optional, can be helpful for certain use cases)
    public HelpCenter() {}

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Override toString method for better object representation
    @Override
    public String toString() {
        return "HelpCenter{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    // Override equals and hashCode for proper comparison and storage in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HelpCenter that = (HelpCenter) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                name.equals(that.name) &&
                address.equals(that.address) &&
                phone.equals(that.phone);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + Double.hashCode(latitude);
        result = 31 * result + Double.hashCode(longitude);
        return result;
    }
}
