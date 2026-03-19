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

public class JobQueueActivity extends AppCompatActivity {

    private TextView jobIdTextView, totalCleanersTextView;
    private LinearLayout cleanersLayout;
    private Button backButton, jobStatusButton, doneButton;

    private String selectedCleanerId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_queue);

        // Initialize UI elements
        jobIdTextView = findViewById(R.id.jobIdTextView);
        totalCleanersTextView = findViewById(R.id.totalCleanersTextView);
        cleanersLayout = findViewById(R.id.cleanersLayout);
        backButton = findViewById(R.id.backButton);
        jobStatusButton = findViewById(R.id.jobStatusButton);
        doneButton = findViewById(R.id.doneButton);

        // Get the Job ID from the intent
        String jobId = getIntent().getStringExtra("JOB_ID");
        jobIdTextView.setText("Job ID: " + jobId);

        // Fetch the list of cleaners who have applied for this job
        List<String> cleanerIds = fetchCleanersForJob(jobId);
        totalCleanersTextView.setText("Total Cleaners: " + cleanerIds.size());

        // Display the list of cleaners
        for (String cleanerId : cleanerIds) {
            addCleanerToLayout(cleanerId);
        }

        // Set click listeners for buttons
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the Customer Dashboard
                Intent intent = new Intent(JobQueueActivity.this, CustomerDashboardActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });

        jobStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCleanerId.isEmpty()) {
                    Toast.makeText(JobQueueActivity.this, "Please select a cleaner", Toast.LENGTH_SHORT).show();
                } else {
                    // Navigate to Job Status Activity
                    Intent intent = new Intent(JobQueueActivity.this, JobStatusActivity.class);
                    intent.putExtra("JOB_ID", jobId);
                    intent.putExtra("CLEANER_ID", selectedCleanerId);
                    startActivity(intent);
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCleanerId.isEmpty()) {
                    Toast.makeText(JobQueueActivity.this, "Please select a cleaner", Toast.LENGTH_SHORT).show();
                } else {
                    // Update the cleaner's "Jobs been selected for" page
                    updateCleanerJobs(selectedCleanerId, jobId);

                    // Navigate back to the Customer Dashboard
                    Intent intent = new Intent(JobQueueActivity.this, CustomerDashboardActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                }
            }
        });
    }

    // Method to fetch cleaners who have applied for the job
    private List<String> fetchCleanersForJob(String jobId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        return dbHelper.fetchCleanersForJob(jobId);
    }

    // Method to add a cleaner to the layout
    private void addCleanerToLayout(String cleanerId) {
        // Create a horizontal LinearLayout for each cleaner
        LinearLayout cleanerLayout = new LinearLayout(this);
        cleanerLayout.setOrientation(LinearLayout.HORIZONTAL);
        cleanerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        cleanerLayout.setPadding(0, 8, 0, 8); // Add padding between cleaner entries


        // Add a CheckBox for selection
        CheckBox checkBox = new CheckBox(this);
        checkBox.setId(View.generateViewId()); // Generate a unique ID for the CheckBox
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedCleanerId = cleanerId;
                // Uncheck all other checkboxes
                for (int i = 0; i < cleanersLayout.getChildCount(); i++) {
                    View child = cleanersLayout.getChildAt(i);
                    if (child instanceof LinearLayout) {
                        CheckBox otherCheckBox = ((LinearLayout) child).findViewById(checkBox.getId()); // Use the generated ID
                        if (otherCheckBox != checkBox) {
                            otherCheckBox.setChecked(false);
                        }
                    }
                }
            } else {
                selectedCleanerId = "";
            }
        });

        // Add a TextView for the cleaner ID
        TextView cleanerIdTextView = new TextView(this);
        cleanerIdTextView.setText(cleanerId);
        cleanerIdTextView.setTextSize(16);
        cleanerIdTextView.setPadding(16, 0, 16, 0);

        // Add a "View" button to see the cleaner's reviews
        Button viewButton = new Button(this);
        viewButton.setText("View");
        viewButton.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        viewButton.setTextColor(getResources().getColor(android.R.color.white));
        viewButton.setPadding(16, 8, 16, 8);
        viewButton.setOnClickListener(v -> {
            // Navigate to View Reviews Activity
            Intent intent = new Intent(JobQueueActivity.this, ViewCustomerReviewsActivity.class);
            intent.putExtra("CLEANER_ID", cleanerId);
            startActivity(intent);
        });

        // Add views to the cleaner layout
        cleanerLayout.addView(checkBox);
        cleanerLayout.addView(cleanerIdTextView);
        cleanerLayout.addView(viewButton);

        // Add the cleaner layout to the main layout
        cleanersLayout.addView(cleanerLayout);
    }

    // Method to update the cleaner's "Jobs been selected for" page
    private void updateCleanerJobs(String cleanerId, String jobId) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean isUpdated = dbHelper.updateCleanerJobs(cleanerId, jobId);

        if (isUpdated) {
            Toast.makeText(this, "Cleaner " + cleanerId + " selected for Job " + jobId, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update cleaner's jobs", Toast.LENGTH_SHORT).show();
        }
    }}