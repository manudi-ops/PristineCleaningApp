package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerDashboardActivity extends AppCompatActivity {

    // Declare buttons for dashboard options
    private Button addJobButton, seeQueueButton, viewJobStatusButton, viewMyReviewsButton, reviewCleanerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        String customerId = getIntent().getStringExtra("CUSTOMER_ID");

        // Initialize buttons
        addJobButton = findViewById(R.id.addJobButton);
        seeQueueButton = findViewById(R.id.seeQueueButton);
        viewJobStatusButton = findViewById(R.id.viewJobStatusButton);
        viewMyReviewsButton = findViewById(R.id.viewMyReviewsButton);
        reviewCleanerButton = findViewById(R.id.reviewCleanerButton);

        // Set click listeners for each button
        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Add Job Activity
                Intent intent = new Intent(CustomerDashboardActivity.this, AddJobActivity.class);
                intent.putExtra("CUSTOMER_ID", customerId);
                startActivity(intent);
            }
        });

        // When "See Queue" button is clicked
        seeQueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboardActivity.this, EnterJobIdActivity.class);
                intent.putExtra("ACTION", "SEE_QUEUE"); // Indicate that the user wants to see the job queue
                startActivity(intent);
            }
        });

// When "View Job Status" button is clicked
        viewJobStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboardActivity.this, EnterJobIdActivity.class);
                intent.putExtra("ACTION", "VIEW_STATUS"); // Indicate that the user wants to view job status
                startActivity(intent);
            }
        });

        viewMyReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Customer's Reviews Activity
                Intent intent = new Intent(CustomerDashboardActivity.this, CustomerReviewsActivity.class);
                startActivity(intent);
            }
        });

        reviewCleanerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Review Cleaner Activity
                Intent intent = new Intent(CustomerDashboardActivity.this, ReviewCleanerActivity.class);
                startActivity(intent);
            }
        });
    }
}