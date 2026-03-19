package com.example.pristinecleaningapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class JobDetailsActivity extends AppCompatActivity {

    // Declare UI elements
    private TextView jobIdTextView, addressTextView, numRoomsTextView, numBathroomsTextView,
            commonAreaTextView, totalSqFtTextView, cleaningTypeTextView, flooringTypeTextView,
            estimatedCostTextView;
    private ImageView jobImageView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);


        // Initialize UI elements
        jobIdTextView = findViewById(R.id.jobIdTextView);
        addressTextView = findViewById(R.id.addressTextView);
        numRoomsTextView = findViewById(R.id.numRoomsTextView);
        numBathroomsTextView = findViewById(R.id.numBathroomsTextView);
        commonAreaTextView = findViewById(R.id.commonAreaTextView);
        totalSqFtTextView = findViewById(R.id.totalSqFtTextView);
        cleaningTypeTextView = findViewById(R.id.cleaningTypeTextView);
        flooringTypeTextView = findViewById(R.id.flooringTypeTextView);
        estimatedCostTextView = findViewById(R.id.estimatedCostTextView);
        jobImageView = findViewById(R.id.jobImageView);
        backButton = findViewById(R.id.backButton);

        // Get the Job ID from the intent
        long jobId = getIntent().getLongExtra("JOB_ID", -1);

        if (jobId != -1) {
            // Fetch job details from the database using the Job ID
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            Job job = dbHelper.getJobById(jobId);

            if (job != null) {
                // Display job details
                jobIdTextView.setText("Job ID: " + job.getJobId());
                addressTextView.setText("Address: " + job.getAddress());
                numRoomsTextView.setText("Number of rooms: " + job.getNumRooms());
                numBathroomsTextView.setText("Number of bathrooms: " + job.getNumBathrooms());
                commonAreaTextView.setText("Other common area included: " + (job.isCommonAreaIncluded() ? "Yes" : "No"));
                totalSqFtTextView.setText("Total space to clean: " + job.getTotalSqFt() + " sq ft");
                cleaningTypeTextView.setText("Cleaning type: " + job.getCleaningType());
                flooringTypeTextView.setText("Flooring type: " + job.getFlooringType());
                estimatedCostTextView.setText("Estimated cost: $" + job.getEstimatedCost());

                // Load the image if available
                if (job.getImageUri() != null && !job.getImageUri().isEmpty()) {
                    jobImageView.setImageURI(Uri.parse(job.getImageUri()));
                }
            } else {
                // Handle case where job details are not found
                jobIdTextView.setText("Job details not found");
            }
        } else {
            // Handle invalid Job ID
            jobIdTextView.setText("Invalid Job ID");
        }

        // Set click listener for the "Back" button
        backButton.setOnClickListener(v -> {
            // Navigate back to the Customer Dashboard
            Intent intent = new Intent(JobDetailsActivity.this, CustomerDashboardActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });


    }
}