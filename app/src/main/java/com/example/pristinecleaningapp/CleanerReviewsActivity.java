package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class CleanerReviewsActivity extends AppCompatActivity {

    private LinearLayout reviewsLayout;
    private Button backButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_reviews);

        // Initialize UI elements
        reviewsLayout = findViewById(R.id.reviewsLayout);
        backButton = findViewById(R.id.backButton);
        dbHelper = new DatabaseHelper(this);

        // Get the Cleaner ID from the intent (assuming it's passed from the Cleaner Dashboard)
        String cleanerId = getIntent().getStringExtra("CLEANER_ID");

        // Fetch the list of customers who have left reviews for this cleaner
        List<String> customerIds = dbHelper.fetchCustomersWhoReviewedCleaner(cleanerId);

        // Display the list of customers
        for (String customerId : customerIds) {
            addCustomerToLayout(customerId);
        }

        // Set click listener for the "Back" button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the Cleaner Dashboard
                Intent intent = new Intent(CleanerReviewsActivity.this, CleanerDashboardActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }

    // Method to add a customer to the layout
    private void addCustomerToLayout(final String customerId) {
        // Create a horizontal LinearLayout for each customer
        LinearLayout customerLayout = new LinearLayout(this);
        customerLayout.setOrientation(LinearLayout.HORIZONTAL);
        customerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        customerLayout.setPadding(0, 8, 0, 8); // Add padding between customer entries

        // Add a TextView for the customer ID
        TextView customerIdTextView = new TextView(this);
        customerIdTextView.setText("Customer ID: " + customerId);
        customerIdTextView.setTextSize(16);
        customerIdTextView.setPadding(16, 0, 16, 0);

        // Add a "View" button to see the review
        Button viewButton = new Button(this);
        viewButton.setText("View");
        viewButton.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        viewButton.setTextColor(getResources().getColor(android.R.color.white));
        viewButton.setPadding(16, 8, 16, 8);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To the Review Details Activity with the Customer ID
                Intent intent = new Intent(CleanerReviewsActivity.this, ReviewDetailsActivity.class);
                intent.putExtra("CUSTOMER_ID", customerId);
                startActivity(intent);
            }
        });

        // Add views to the customer layout
        customerLayout.addView(customerIdTextView);
        customerLayout.addView(viewButton);

        // Add the customer layout to the main layout
        reviewsLayout.addView(customerLayout);
    }
}