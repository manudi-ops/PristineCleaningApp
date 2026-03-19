package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JobStatusActivity extends AppCompatActivity {

    private TextView jobIdTextView, cleanerIdTextView, statusTextView;
    private Button deleteButton, updateButton, backButton;

    private String jobId;
    private String jobStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_status);

        // Initialize UI elements
        jobIdTextView = findViewById(R.id.jobIdTextView);
        cleanerIdTextView = findViewById(R.id.cleanerIdTextView);
        statusTextView = findViewById(R.id.statusTextView);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
        backButton = findViewById(R.id.backButton);

        // Get the Job ID from the intent
        jobId = getIntent().getStringExtra("JOB_ID");
        jobIdTextView.setText("Job ID: " + jobId);

        // Fetch job status and cleaner ID from the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Job job = dbHelper.getJobById(Long.parseLong(jobId));

        if (job != null) {
            jobStatus = job.getStatus();
            statusTextView.setText("Status: " + jobStatus);

            // Fetch the cleaner ID (if any)
            String cleanerId = dbHelper.getCleanerIdForJob(jobId);
            if (cleanerId != null) {
                cleanerIdTextView.setText("Cleaner ID: " + cleanerId);
            } else {
                cleanerIdTextView.setText("Cleaner ID: Not assigned");
            }

            // Enable/disable buttons based on job status
            if (jobStatus.equals("Accepted")) {
                deleteButton.setEnabled(false);
                updateButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
                updateButton.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "Job not found", Toast.LENGTH_SHORT).show();
            finish(); // Closes the activity if the job is not found
        }

        // Set click listeners for buttons
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the job from the database
                boolean isDeleted = dbHelper.deleteJob(jobId);
                if (isDeleted) {
                    Toast.makeText(JobStatusActivity.this, "Job deleted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(JobStatusActivity.this, "Failed to delete job", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Add Job Activity with the existing job details
                Intent intent = new Intent(JobStatusActivity.this, AddJobActivity.class);
                intent.putExtra("JOB_ID", jobId);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the Customer Dashboard
                Intent intent = new Intent(JobStatusActivity.this, CustomerDashboardActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }
}