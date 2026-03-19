package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CleanerDashboardActivity extends AppCompatActivity {

    private Button applyForJobsButton, jobsIGotButton, viewMyReviewsButton, reviewCustomerButton;
    private String cleanerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_dashboard);

        // Initialize UI elements
        applyForJobsButton = findViewById(R.id.applyForJobsButton);
        jobsIGotButton = findViewById(R.id.jobsIGotButton);
        viewMyReviewsButton = findViewById(R.id.viewMyReviewsButton);
        reviewCustomerButton = findViewById(R.id.reviewCustomerButton);

        // When "Apply for Jobs" button is clicked
        applyForJobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Apply for Jobs Activity
                Intent intent = new Intent(CleanerDashboardActivity.this, ApplyForJobsActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId); // Pass the Cleaner ID
                startActivity(intent);
            }
        });

        // When "Jobs I Got" button is clicked
        jobsIGotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Jobs Gotten Activity
                Intent intent = new Intent(CleanerDashboardActivity.this, JobsGottenActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId); // Pass the Cleaner ID
                startActivity(intent);
            }
        });

        // When "View My Reviews" button is clicked
        viewMyReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Cleaner Reviews Activity
                Intent intent = new Intent(CleanerDashboardActivity.this, CleanerReviewsActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId); // Pass the Cleaner ID
                startActivity(intent);
            }
        });

        // When "Review Customer" button is clicked
        reviewCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Review Customer Activity
                Intent intent = new Intent(CleanerDashboardActivity.this, ReviewCustomerActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId); // Pass the Cleaner ID
                startActivity(intent);
            }
        });
    }
}