package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CleanerLoginActivity extends AppCompatActivity {

    private EditText cleanerIdEditText, passwordEditText;
    private Button loginButton, updateButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_login);

        dbHelper = new DatabaseHelper(this);

        cleanerIdEditText = findViewById(R.id.cleanerIdEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        updateButton = findViewById(R.id.updateButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cleanerId = cleanerIdEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (cleanerId.isEmpty() || password.isEmpty()) {
                    Toast.makeText(CleanerLoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (dbHelper.checkCleaner(cleanerId, password)) {
                        Toast.makeText(CleanerLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CleanerLoginActivity.this, CleanerDashboardActivity.class);
                        intent.putExtra("CLEANER_ID", cleanerId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CleanerLoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cleanerId = cleanerIdEditText.getText().toString().trim();

                if (cleanerId.isEmpty()) {
                    Toast.makeText(CleanerLoginActivity.this, "Please enter your Cleaner ID", Toast.LENGTH_SHORT).show();
                } else {
                    // Navigate to Cleaner Register Activity with the Cleaner ID
                    Intent intent = new Intent(CleanerLoginActivity.this, CleanerRegisterActivity.class);
                    intent.putExtra("CLEANER_ID", cleanerId);
                    startActivity(intent);
                }
            }
        });
    }
}