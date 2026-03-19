package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterJobIdActivity extends AppCompatActivity {

    private EditText jobIdEditText;
    private Button doneButton;

    private String action; // This is to determine whether to navigate to Job Queue or Job Status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_id);

        // Initialize UI elements
        jobIdEditText = findViewById(R.id.jobIdEditText);
        doneButton = findViewById(R.id.doneButton);

        // Get the action from the intent (whether to navigate to Job Queue or Job Status)
        action = getIntent().getStringExtra("ACTION");

        // Set click listener for the "Done" button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobId = jobIdEditText.getText().toString().trim();

                if (jobId.isEmpty()) {
                    Toast.makeText(EnterJobIdActivity.this, "Please enter a Job ID", Toast.LENGTH_SHORT).show();
                } else {
                    // Navigate to the appropriate activity based on the action
                    Intent intent;
                    if (action != null) {
                        switch (action) {
                            case "SEE_QUEUE":
                                intent = new Intent(EnterJobIdActivity.this, JobQueueActivity.class);
                                break;
                            case "VIEW_STATUS":
                                intent = new Intent(EnterJobIdActivity.this, JobStatusActivity.class);
                                break;
                            default:
                                intent = new Intent(EnterJobIdActivity.this, CustomerDashboardActivity.class);
                                break;
                        }
                        intent.putExtra("JOB_ID", jobId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EnterJobIdActivity.this, "Invalid action", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}