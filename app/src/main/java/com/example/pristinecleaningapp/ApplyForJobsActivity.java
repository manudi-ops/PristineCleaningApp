package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ApplyForJobsActivity extends AppCompatActivity {

    private LinearLayout jobsLayout;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_jobs);

        jobsLayout = findViewById(R.id.jobsLayout);
        confirmButton = findViewById(R.id.confirmButton);

        // Fetch the list of vacant jobs from the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Job> vacantJobs = dbHelper.fetchVacantJobs();

        // Display the list of vacant jobs
        for (Job job : vacantJobs) {
            addJobToLayout(job);
        }

        // Set click listener for the "Confirm" button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the Job Queue with the selected jobs
                for (int i = 0; i < jobsLayout.getChildCount(); i++) {
                    View child = jobsLayout.getChildAt(i);
                    if (child instanceof LinearLayout) {
                        LinearLayout jobLayout = (LinearLayout) child;
                        CheckBox checkBox = (CheckBox) jobLayout.getChildAt(0); // CheckBox is the first child
                        if (checkBox.isChecked()) {
                            String jobId = checkBox.getTag().toString();
                            dbHelper.applyForJob(jobId, getIntent().getStringExtra("CLEANER_ID"));
                        }
                    }
                }
                Toast.makeText(ApplyForJobsActivity.this, "Jobs applied successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity
            }
        });
    }

    // Method to add a job to the layout
    private void addJobToLayout(Job job) {
        // Create a horizontal LinearLayout for each job
        LinearLayout jobLayout = new LinearLayout(this);
        jobLayout.setOrientation(LinearLayout.HORIZONTAL);
        jobLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        jobLayout.setPadding(0, 8, 0, 8); // Adds padding between job entries

        // Add a CheckBox for selection
        CheckBox checkBox = new CheckBox(this);
        checkBox.setId(View.generateViewId()); // Generate a unique ID for the CheckBox
        checkBox.setTag(job.getJobId()); // Store the Job ID in the CheckBox tag

        // Add a TextView for the Job ID
        TextView jobIdTextView = new TextView(this);
        jobIdTextView.setText("Job ID: " + job.getJobId());
        jobIdTextView.setTextSize(16);
        jobIdTextView.setPadding(16, 0, 16, 0);

        // Add a "View" button to see the job details
        Button viewJobButton = new Button(this);
        viewJobButton.setText("View Job");
        viewJobButton.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        viewJobButton.setTextColor(getResources().getColor(android.R.color.white));
        viewJobButton.setPadding(16, 8, 16, 8);
        viewJobButton.setOnClickListener(v -> {
            // Navigate to Job Details Activity
            Intent intent = new Intent(ApplyForJobsActivity.this, JobDetailsActivity.class);
            intent.putExtra("JOB_ID", job.getJobId());
            startActivity(intent);
        });

        // Add a "View Reviews" button to see the customer's reviews
        Button viewReviewsButton = new Button(this);
        viewReviewsButton.setText("View Reviews");
        viewReviewsButton.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        viewReviewsButton.setTextColor(getResources().getColor(android.R.color.white));
        viewReviewsButton.setPadding(16, 8, 16, 8);
        viewReviewsButton.setOnClickListener(v -> {
            // Navigate to View Reviews Activity
            Intent intent = new Intent(ApplyForJobsActivity.this, ViewCustomerReviewsActivity.class);
            intent.putExtra("CUSTOMER_ID", job.getCustomerId());
            startActivity(intent);
        });

        // Add views to the job layout
        jobLayout.addView(checkBox);
        jobLayout.addView(jobIdTextView);
        jobLayout.addView(viewJobButton);
        jobLayout.addView(viewReviewsButton);

        // Add the job layout to the main layout
        jobsLayout.addView(jobLayout);
    }
}