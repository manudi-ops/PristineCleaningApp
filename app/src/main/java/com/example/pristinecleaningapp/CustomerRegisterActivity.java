package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerRegisterActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText fullNameEditText, addressEditText, emailEditText, passwordEditText, phoneEditText;
    private TextView customerIdTextView;
    private Button confirmButton;
    private DatabaseHelper dbHelper;

    private String customerId; // To store the Customer ID for updates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        fullNameEditText = findViewById(R.id.fullNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        confirmButton = findViewById(R.id.confirmButton);
        customerIdTextView = findViewById(R.id.customerIdTextView);

        // Check if this is an update request
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("CUSTOMER_ID")) {
            customerId = extras.getString("CUSTOMER_ID");
            customerIdTextView.setText("Your Customer ID: " + customerId);
            customerIdTextView.setVisibility(View.VISIBLE); // Show the Customer ID
        } else {
            customerIdTextView.setVisibility(View.GONE); // Hide the Customer ID for new registrations
        }

        // Set click listener for the "Confirm registration and create account" button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String fullName = fullNameEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                // Validate inputs
                if (fullName.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(CustomerRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(CustomerRegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhone(phone)) {
                    Toast.makeText(CustomerRegisterActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                } else {
                    if (customerId != null) {
                        // Update existing customer details
                        boolean isUpdated = dbHelper.updateCustomer(customerId, fullName, address, email, password, phone);
                        if (isUpdated) {
                            Toast.makeText(CustomerRegisterActivity.this, "Account Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CustomerRegisterActivity.this, CustomerDashboardActivity.class);
                            startActivity(intent);
                            finish(); // Close the current activity
                        } else {
                            Toast.makeText(CustomerRegisterActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Insert new customer data into the database
                        long newCustomerId = dbHelper.insertCustomer(fullName, address, email, password, phone);
                        if (newCustomerId != -1) {
                            customerIdTextView.setText("Your Customer ID: " + newCustomerId);
                            Toast.makeText(CustomerRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CustomerRegisterActivity.this, CustomerDashboardActivity.class);
                            startActivity(intent);
                            finish(); // Close the current activity
                        } else {
                            Toast.makeText(CustomerRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    // Validate email format
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Validate phone number format (10 digits)
    private boolean isValidPhone(String phone) {
        return phone.length() == 10 && phone.matches("\\d+");
    }
}