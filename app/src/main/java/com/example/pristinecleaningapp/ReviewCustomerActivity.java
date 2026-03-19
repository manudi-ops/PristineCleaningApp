package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewCustomerActivity extends AppCompatActivity {

    private EditText customerIdEditText, reviewEditText;
    private Button addButton, backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_customer);

        // Initialize UI elements
        customerIdEditText = findViewById(R.id.customerIdEditText);
        reviewEditText = findViewById(R.id.reviewEditText);
        addButton = findViewById(R.id.addButton);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        // Set click listener for the "Add" button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerId = customerIdEditText.getText().toString().trim();
                String reviewText = reviewEditText.getText().toString().trim();

                if (customerId.isEmpty() || reviewText.isEmpty()) {
                    Toast.makeText(ReviewCustomerActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert the review into the database
                    long result = dbHelper.insertReview(getIntent().getStringExtra("CLEANER_ID"), customerId, reviewText);

                    if (result != -1) {
                        Toast.makeText(ReviewCustomerActivity.this, "Review added successfully", Toast.LENGTH_SHORT).show();
                        // Navigate back to the Cleaner Dashboard
                        Intent intent = new Intent(ReviewCustomerActivity.this, CleanerDashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ReviewCustomerActivity.this, "Failed to add review", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set click listener for the "Back" button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the Cleaner Dashboard
                Intent intent = new Intent(ReviewCustomerActivity.this, CleanerDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}