package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerLoginActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText customerIdEditText, passwordEditText;
    private Button loginButton, updateButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        customerIdEditText = findViewById(R.id.customerIdEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        updateButton = findViewById(R.id.updateButton);

        // Set click listener for the "Log in" button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String customerId = customerIdEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate inputs
                if (customerId.isEmpty() || password.isEmpty()) {
                    Toast.makeText(CustomerLoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the customer ID and password match the database records
                    if (dbHelper.checkCustomer(customerId, password)) {
                        Toast.makeText(CustomerLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Navigate to Customer Dashboard
                        Intent intent = new Intent(CustomerLoginActivity.this, CustomerDashboardActivity.class);
                        startActivity(intent);
                        finish(); // Close the current activity
                    } else {
                        Toast.makeText(CustomerLoginActivity.this, "Invalid Customer ID or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set click listener for the "Update account" button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the Customer ID
                String customerId = customerIdEditText.getText().toString().trim();

                // Validate Customer ID
                if (customerId.isEmpty()) {
                    Toast.makeText(CustomerLoginActivity.this, "Please enter your Customer ID", Toast.LENGTH_SHORT).show();
                } else {
                    // Navigate to Customer Register Activity with the Customer ID
                    Intent intent = new Intent(CustomerLoginActivity.this, CustomerRegisterActivity.class);
                    intent.putExtra("CUSTOMER_ID", customerId); // Pass the Customer ID to the next activity
                    startActivity(intent);
                }
            }
        });
    }
}