package com.example.pristinecleaningapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddJobActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText numRoomsEditText, numBathroomsEditText, totalSqFtEditText;
    private CheckBox commonAreaCheckBox, generalCleaningCheckBox, heavyCleaningCheckBox;
    private RadioGroup flooringTypeRadioGroup;
    private TextView estimatedCostTextView;
    private Button postJobButton, uploadImageButton;
    private ImageView jobImageView;

    private DatabaseHelper dbHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        numRoomsEditText = findViewById(R.id.numRoomsEditText);
        numBathroomsEditText = findViewById(R.id.numBathroomsEditText);
        totalSqFtEditText = findViewById(R.id.totalSqFtEditText);
        commonAreaCheckBox = findViewById(R.id.commonAreaCheckBox);
        generalCleaningCheckBox = findViewById(R.id.generalCleaningCheckBox);
        heavyCleaningCheckBox = findViewById(R.id.heavyCleaningCheckBox);
        flooringTypeRadioGroup = findViewById(R.id.flooringTypeRadioGroup);
        estimatedCostTextView = findViewById(R.id.estimatedCostTextView);
        postJobButton = findViewById(R.id.postJobButton);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        jobImageView = findViewById(R.id.jobImageView);

        // Setting click listener for the "Upload Image" button
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        // Set click listener for the "Post job" button
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String numRooms = numRoomsEditText.getText().toString().trim();
                String numBathrooms = numBathroomsEditText.getText().toString().trim();
                String totalSqFt = totalSqFtEditText.getText().toString().trim();
                boolean commonAreaIncluded = commonAreaCheckBox.isChecked();
                boolean generalCleaning = generalCleaningCheckBox.isChecked();
                boolean heavyCleaning = heavyCleaningCheckBox.isChecked();
                int selectedFlooringTypeId = flooringTypeRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedFlooringType = findViewById(selectedFlooringTypeId);
                String flooringType = selectedFlooringType != null ? selectedFlooringType.getText().toString() : "";

                // Validate inputs
                if (numRooms.isEmpty() || numBathrooms.isEmpty() || totalSqFt.isEmpty() || flooringType.isEmpty()) {
                    Toast.makeText(AddJobActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!generalCleaning && !heavyCleaning) {
                    Toast.makeText(AddJobActivity.this, "Please select a cleaning type", Toast.LENGTH_SHORT).show();
                } else {
                    // Calculate estimated cost
                    double estimatedCost = calculateEstimatedCost(
                            Integer.parseInt(numRooms),
                            Integer.parseInt(numBathrooms),
                            Double.parseDouble(totalSqFt),
                            commonAreaIncluded,
                            generalCleaning,
                            heavyCleaning,
                            flooringType
                    );

                    // Display estimated cost
                    estimatedCostTextView.setText("Estimated Cost: $" + estimatedCost);

                    // Insert job details into the database
                    long jobId = dbHelper.insertJob(
                            getIntent().getStringExtra("CUSTOMER_ID"), // Pass Customer ID from previous activity
                            Integer.parseInt(numRooms),
                            Integer.parseInt(numBathrooms),
                            commonAreaIncluded ? 1 : 0,
                            Double.parseDouble(totalSqFt),
                            generalCleaning ? "General" : "Heavy",
                            flooringType,
                            estimatedCost,
                            "Pending", // Default status
                            imageUri != null ? imageUri.toString() : "" // Store image URI as a string
                    );

                    if (jobId != -1) {
                        Toast.makeText(AddJobActivity.this, "Job Posted Successfully", Toast.LENGTH_SHORT).show();

                        // Navigate to Job Details Activity with the Job ID
                        Intent intent = new Intent(AddJobActivity.this, JobDetailsActivity.class);
                        intent.putExtra("JOB_ID", jobId);
                        startActivity(intent);
                        finish(); // Close the current activity
                    } else {
                        Toast.makeText(AddJobActivity.this, "Failed to Post Job", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Method to calculate estimated cost
    private double calculateEstimatedCost(int numRooms, int numBathrooms, double totalSqFt, boolean commonAreaIncluded, boolean generalCleaning, boolean heavyCleaning, String flooringType) {
        double baseCost = 50; // Base cost
        double roomCost = numRooms * 10;
        double bathroomCost = numBathrooms * 15;
        double sqFtCost = totalSqFt * 0.1;
        double commonAreaCost = commonAreaIncluded ? 20 : 0;
        double cleaningTypeCost = heavyCleaning ? 30 : 0;
        double flooringCost = flooringType.equals("Carpet") ? 10 : 0; // Have to add more logic for other flooring types

        return baseCost + roomCost + bathroomCost + sqFtCost + commonAreaCost + cleaningTypeCost + flooringCost;
    }

    // Method to open the image picker
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            jobImageView.setImageURI(imageUri);
        }
    }
}