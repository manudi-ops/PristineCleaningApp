package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class CustomerReviewsActivity extends AppCompatActivity {

    private LinearLayout reviewsLayout;
    private Button backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reviews);

        // Initialize UI elements
        reviewsLayout = findViewById(R.id.reviewsLayout);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        // Get the Customer ID from the intent (assuming it's passed from the Customer Dashboard)
        String customerId = getIntent().getStringExtra("CUSTOMER_ID");

        // Fetch the list of cleaners who have left reviews for this customer
        List<String> cleanerIds = dbHelper.fetchCleanersWhoReviewedCustomer(customerId);

        // Display the list of cleaners
        for (String cleanerId : cleanerIds) {
            addCleanerToLayout(cleanerId);
        }

        // Set click listener for the "Back" button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the Customer Dashboard
                Intent intent = new Intent(CustomerReviewsActivity.this, CustomerDashboardActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }

    // Method to add a cleaner to the layout
    private void addCleanerToLayout(final String cleanerId) {
        // Create a horizontal LinearLayout for each cleaner
        LinearLayout cleanerLayout = new LinearLayout(this);
        cleanerLayout.setOrientation(LinearLayout.HORIZONTAL);
        cleanerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        cleanerLayout.setPadding(0, 8, 0, 8); //padding

        // Add a TextView for the cleaner ID
        TextView cleanerIdTextView = new TextView(this);
        cleanerIdTextView.setText("Cleaner ID: " + cleanerId);
        cleanerIdTextView.setTextSize(16);
        cleanerIdTextView.setPadding(16, 0, 16, 0);

        // Add a "View" button to see the review
        Button viewButton = new Button(this);
        viewButton.setText("View");
        viewButton.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        viewButton.setTextColor(getResources().getColor(android.R.color.white));
        viewButton.setPadding(16, 8, 16, 8);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To the Review Details Activity with the Cleaner ID
                Intent intent = new Intent(CustomerReviewsActivity.this, ReviewDetailsActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId);
                startActivity(intent);
            }
        });

        // Add views to the cleaner layout
        cleanerLayout.addView(cleanerIdTextView);
        cleanerLayout.addView(viewButton);

        // Add the cleaner layout to the main layout
        reviewsLayout.addView(cleanerLayout);
    }
}