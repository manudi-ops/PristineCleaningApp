package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CleanerAccountAccessActivity extends AppCompatActivity {

    private Button registerButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_account_access);

        // Initialize UI elements
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        // click listener for the "Register" button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Cleaner Register Activity
                Intent intent = new Intent(CleanerAccountAccessActivity.this, CleanerRegisterActivity.class);
                startActivity(intent);
            }
        });

        // Click listener for the "Login" button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Cleaner Login Activity
                Intent intent = new Intent(CleanerAccountAccessActivity.this, CleanerLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}