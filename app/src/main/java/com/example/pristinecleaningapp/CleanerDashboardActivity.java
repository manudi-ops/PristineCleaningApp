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

        // Fixing Retrieve cleanerId from the intent
        cleanerId = getIntent().getStringExtra("CLEANER_ID");

        // Initialize UI elements
        applyForJobsButton = findViewById(R.id.applyForJobsButton);
        jobsIGotButton = findViewById(R.id.jobsIGotButton);
        viewMyReviewsButton = findViewById(R.id.viewMyReviewsButton);
        reviewCustomerButton = findViewById(R.id.reviewCustomerButton);

        applyForJobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CleanerDashboardActivity.this, ApplyForJobsActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId);
                startActivity(intent);
            }
        });

        jobsIGotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CleanerDashboardActivity.this, JobsGottenActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId);
                startActivity(intent);
            }
        });

        viewMyReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CleanerDashboardActivity.this, CleanerReviewsActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId);
                startActivity(intent);
            }
        });

        reviewCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CleanerDashboardActivity.this, ReviewCustomerActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId);
                startActivity(intent);
            }
        });
    }
}