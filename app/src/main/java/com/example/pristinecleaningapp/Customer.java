package com.example.pristinecleaningapp;

public class Customer {
    private String customerId;
    private String fullName;
    private String address;
    private String email;
    private String password;
    private String phoneNumber;

    // Constructor
    public Customer(String customerId, String fullName, String address, String email, String password, String phoneNumber) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
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