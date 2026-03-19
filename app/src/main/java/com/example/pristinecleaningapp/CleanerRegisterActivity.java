package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CleanerRegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText, phoneEditText;
    private Button confirmButton;
    private DatabaseHelper dbHelper;
    private String cleanerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_register);

        dbHelper = new DatabaseHelper(this);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        confirmButton = findViewById(R.id.confirmButton);

        // Check if this is an update request
        Intent intent = getIntent();
        if (intent.hasExtra("CLEANER_ID")) {
            cleanerId = intent.getStringExtra("CLEANER_ID");
            // Fetch the existing cleaner details and pre-fill the form
            Cleaner cleaner = dbHelper.getCleanerById(cleanerId);
            if (cleaner != null) {
                fullNameEditText.setText(cleaner.getFullName());
                emailEditText.setText(cleaner.getEmail());
                passwordEditText.setText(cleaner.getPassword());
                phoneEditText.setText(cleaner.getPhoneNumber());
            }
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(CleanerRegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (cleanerId != null) {
                        // Update existing cleaner details
                        boolean isUpdated = dbHelper.updateCleaner(cleanerId, fullName, email, password, phone);
                        if (isUpdated) {
                            Toast.makeText(CleanerRegisterActivity.this, "Account Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CleanerRegisterActivity.this, CleanerDashboardActivity.class);
                            startActivity(intent);
                            finish(); // Close the current activity
                        } else {
                            Toast.makeText(CleanerRegisterActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Insert new cleaner data into the database
                        long newCleanerId = dbHelper.insertCleaner(fullName, email, password, phone);
                        if (newCleanerId != -1) {
                            Toast.makeText(CleanerRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CleanerRegisterActivity.this, CleanerDashboardActivity.class);
                            startActivity(intent);
                            finish(); // Close the current activity
                        } else {
                            Toast.makeText(CleanerRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}