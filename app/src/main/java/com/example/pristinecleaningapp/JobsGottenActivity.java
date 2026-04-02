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

public class JobsGottenActivity extends AppCompatActivity {

    private LinearLayout jobsLayout;
    private Button confirmButton;
    private DatabaseHelper dbHelper;
    private String cleanerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_gotten);

        // Initialize UI elements
        jobsLayout = findViewById(R.id.jobsLayout);
        confirmButton = findViewById(R.id.confirmButton);
        dbHelper = new DatabaseHelper(this);

        // Get the Cleaner ID from the intent
        cleanerId = getIntent().getStringExtra("CLEANER_ID");

        // Fetch the list of jobs the cleaner has been selected for
        List<Job> jobs = dbHelper.fetchJobsForCleaner(cleanerId);

        // Display the list of jobs
        for (Job job : jobs) {
            addJobToLayout(job);
        }

        // Set click listener for the "Confirm" button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < jobsLayout.getChildCount(); i++) {
                    View child = jobsLayout.getChildAt(i);
                    if (child instanceof LinearLayout) {
                        LinearLayout jobLayout = (LinearLayout) child;

                        // FIX: Find checkboxes by their index position in the layout
                        CheckBox getCheckBox = (CheckBox) jobLayout.getChildAt(3);
                        CheckBox cancelCheckBox = (CheckBox) jobLayout.getChildAt(4);
                        TextView jobIdTextView = (TextView) jobLayout.getChildAt(0);

                        String jobId = jobIdTextView.getText().toString().replace("Job ID: ", "");
                        if (getCheckBox.isChecked()) {
                            dbHelper.updateJobStatus(jobId, "Accepted");
                        } else if (cancelCheckBox.isChecked()) {
                            dbHelper.updateJobStatus(jobId, "Cancelled");
                        }
                    }
                }
                Toast.makeText(JobsGottenActivity.this, "Job statuses updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JobsGottenActivity.this, CleanerDashboardActivity.class);
                intent.putExtra("CLEANER_ID", cleanerId);
                startActivity(intent);
                finish();
            }
        });
    }

    // Method to add a job to the layout
    private void addJobToLayout(final Job job) {
        // Create a vertical LinearLayout for each job
        LinearLayout jobLayout = new LinearLayout(this);
        jobLayout.setOrientation(LinearLayout.VERTICAL);
        jobLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        jobLayout.setPadding(0, 8, 0, 8); // Add padding between job entries

        // Add a TextView for the Job ID
        TextView jobIdTextView = new TextView(this);
        jobIdTextView.setText("Job ID: " + job.getJobId());
        jobIdTextView.setTextSize(16);
        jobIdTextView.setPadding(16, 0, 16, 0);

        // Add a "View Customer Reviews" button
        Button viewCustomerReviewsButton = new Button(this);
        viewCustomerReviewsButton.setText("View Customer Reviews");
        viewCustomerReviewsButton.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        viewCustomerReviewsButton.setTextColor(getResources().getColor(android.R.color.white));
        viewCustomerReviewsButton.setPadding(16, 8, 16, 8);
        viewCustomerReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to View Reviews Activity with the Customer ID
                Intent intent = new Intent(JobsGottenActivity.this, ViewReviewsActivity.class);
                intent.putExtra("CUSTOMER_ID", job.getCustomerId());
                startActivity(intent);
            }
        });

        // Add a "View Job Details" button
        Button viewJobDetailsButton = new Button(this);
        viewJobDetailsButton.setText("View Job Details");
        viewJobDetailsButton.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        viewJobDetailsButton.setTextColor(getResources().getColor(android.R.color.white));
        viewJobDetailsButton.setPadding(16, 8, 16, 8);
        viewJobDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Job Details Activity with the Job ID
                Intent intent = new Intent(JobsGottenActivity.this, JobDetailsActivity.class);
                intent.putExtra("JOB_ID", job.getJobId());
                startActivity(intent);
            }
        });

        // Add CheckBoxes for "Get" and "Cancel"
        CheckBox getCheckBox = new CheckBox(this);
        getCheckBox.setText("Get");
        getCheckBox.setPadding(16, 0, 16, 0);

        CheckBox cancelCheckBox = new CheckBox(this);
        cancelCheckBox.setText("Cancel");
        cancelCheckBox.setPadding(16, 0, 16, 0);
        cancelCheckBox.setId(View.generateViewId()); // Assign a unique ID to the CheckBox

        // Add views to the job layout
        jobLayout.addView(jobIdTextView);
        jobLayout.addView(viewCustomerReviewsButton);
        jobLayout.addView(viewJobDetailsButton);
        jobLayout.addView(getCheckBox);
        jobLayout.addView(cancelCheckBox);

        // Add the job layout to the main layout
        jobsLayout.addView(jobLayout);
    }
}