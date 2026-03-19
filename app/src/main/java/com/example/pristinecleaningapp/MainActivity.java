package com.example.pristinecleaningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button giveJobButton;
    private Button getJobButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        giveJobButton = findViewById(R.id.giveJobButton);
        getJobButton = findViewById(R.id.getJobButton);

        giveJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For now, just show a Toast message, I can later replace it with job creation logic
                Toast.makeText(MainActivity.this, "Give Job button clicked", Toast.LENGTH_SHORT).show();

                // Can later launch an activity to give a new job
                Intent intent = new Intent(MainActivity.this,CustomerAccountAccessActivity.class);
                startActivity(intent);
            }
        });

        getJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For now
                Toast.makeText(MainActivity.this, "Get Job button clicked", Toast.LENGTH_SHORT).show();

                // Navigate to Cleaner Account Access Activity
                Intent intent = new Intent(MainActivity.this, CleanerAccountAccessActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showAvailableJobs() {

        // Assumed that there's a method that retrieves jobs from the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_JOB,
                new String[]{DatabaseHelper.COL_JOB_ID, DatabaseHelper.COL_JOB_STATUS},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0) {
            StringBuilder jobs = new StringBuilder();
            while (cursor.moveToNext()) {
                int jobIdIndex = cursor.getColumnIndex(DatabaseHelper.CQL_JOB_ID);
                int jobStatusIndex = cursor.getColumnIndex(DatabaseHelper.CQL_JOB_STATUS);

                if (jobIdIndex >= 0 && jobStatusIndex >= 0) {
                    String jobId = cursor.getString(jobIdIndex);
                    String jobStatus = cursor.getString(jobStatusIndex);
                    jobs.append("Job ID: ").append(jobId).append(", Status: ").append(jobStatus).append("\n");
                } else {
                    // Handles the case where the column does not exist
                    Toast.makeText(MainActivity.this, "Column not found", Toast.LENGTH_SHORT).show();
                }
            }
            // Display jobs in a Toast
            Toast.makeText(MainActivity.this, jobs.toString(), Toast.LENGTH_LONG).show();
            cursor.close();
        } else {
            Toast.makeText(MainActivity.this, "No jobs available", Toast.LENGTH_SHORT).show();
        }
    }
}
