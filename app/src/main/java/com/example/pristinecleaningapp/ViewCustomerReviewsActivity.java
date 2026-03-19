package com.example.pristinecleaningapp;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewCustomerReviewsActivity extends AppCompatActivity {

    private LinearLayout reviewsLayout;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_reviews);

        // Initialize UI elements
        reviewsLayout = findViewById(R.id.reviewsLayout);
        dbHelper = new DatabaseHelper(this);

        // Get the Customer ID from the intent
        String customerId = getIntent().getStringExtra("CUSTOMER_ID");

        // Fetch the reviews for this customer
        List<Review> reviews = dbHelper.fetchReviewsForCustomer(customerId);

        // Display the reviews
        for (Review review : reviews) {
            addReviewToLayout(review);
        }
    }

    // Method to add a review to the layout
    private void addReviewToLayout(Review review) {
        // Create a TextView for each review
        TextView reviewTextView = new TextView(this);
        reviewTextView.setText("Reviewer ID: " + review.getReviewerId() + "\nReview: " + review.getReviewText());
        reviewTextView.setTextSize(16);
        reviewTextView.setPadding(16, 8, 16, 8);

        // Add the review TextView to the main layout
        reviewsLayout.addView(reviewTextView);
    }
}