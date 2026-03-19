package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewDetailsActivity extends AppCompatActivity {

    private TextView reviewTextView;
    private Button doneButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        // Initialize UI elements
        reviewTextView = findViewById(R.id.reviewTextView);
        doneButton = findViewById(R.id.doneButton);
        dbHelper = new DatabaseHelper(this);

        // Get the ID (Cleaner or Customer) from the intent
        String cleanerId = getIntent().getStringExtra("CLEANER_ID");
        String customerId = getIntent().getStringExtra("CUSTOMER_ID");

        String reviewText = "";

        // Fetch the review based on the ID passed
        if (cleanerId != null) {
            reviewText = dbHelper.fetchReviewForCleaner(cleanerId);
        } else if (customerId != null) {
            reviewText = dbHelper.fetchReviewForCustomer(customerId);
        }

        // Display the review
        reviewTextView.setText(reviewText);

        // Set click listener for the "Done" button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the appropriate page based on the ID passed
                Intent intent;
                if (cleanerId != null) {
                    intent = new Intent(ReviewDetailsActivity.this, CustomerReviewsActivity.class);
                } else {
                    intent = new Intent(ReviewDetailsActivity.this, CleanerReviewsActivity.class);
                }
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }
}