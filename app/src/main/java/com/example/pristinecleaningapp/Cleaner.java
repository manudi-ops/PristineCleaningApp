package com.example.pristinecleaningapp;

public class Cleaner {
    private String cleanerId;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

    // Constructor
    public Cleaner(String cleanerId, String fullName, String email, String password, String phoneNumber) {
        this.cleanerId = cleanerId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getCleanerId() {
        return cleanerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}