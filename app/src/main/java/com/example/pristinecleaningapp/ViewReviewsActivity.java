package com.example.pristinecleaningapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewReviewsActivity extends AppCompatActivity {

    private TextView reviewsTextView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);

        reviewsTextView = findViewById(R.id.reviewsTextView);
        dbHelper = new DatabaseHelper(this);

        String customerId = getIntent().getStringExtra("CUSTOMER_ID");
        displayReviews(customerId);
    }

    private void displayReviews(String customerId) {
        List<Review> reviews = dbHelper.fetchReviewsForCustomer(customerId);

        if (reviews.isEmpty()) {
            reviewsTextView.setText("No reviews yet.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Review review : reviews) {
            sb.append("Reviewer ID: ").append(review.getReviewerId())
                    .append("\nReview: ").append(review.getReviewText())
                    .append("\n\n");
        }
        reviewsTextView.setText(sb.toString());
    }
}
