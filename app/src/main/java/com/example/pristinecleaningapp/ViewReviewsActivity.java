package com.example.pristinecleaningapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewReviewsActivity extends AppCompatActivity {

    private TextView reviewsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        // Initialize UI elements
        reviewsTextView = findViewById(R.id.reviewsTextView);

        // Get the Customer ID from the intent
        String customerId = getIntent().getStringExtra("CUSTOMER_ID");

        // Fetch and display reviews for the customer
        displayReviews(customerId);
    }

    // Method to fetch and display reviews
    private void displayReviews(String customerId) {
        // Fetch reviews from the database or a mock data source
        String reviews = fetchReviewsFromDatabase(customerId);

        // Display the reviews in the TextView
        reviewsTextView.setText(reviews);
    }

    // Mock method to fetch reviews
    private String fetchReviewsFromDatabase(String customerId) {
        // Have to replace this with actual database query logic
        return "Customer ID: " + customerId + "\n\n" +
                "Review 1: Great service!\n" +
                "Review 2: Very professional.\n" +
                "Review 3: Would recommend!";
    }
}