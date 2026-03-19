package com.example.pristinecleaningapp;

public class Review {
    private String reviewId;
    private String reviewerId;
    private String revieweeId;
    private String reviewText;

    // Constructor
    public Review(String reviewId, String reviewerId, String revieweeId, String reviewText) {
        this.reviewId = reviewId;
        this.reviewerId = reviewerId;
        this.revieweeId = revieweeId;
        this.reviewText = reviewText;
    }

    // Getters
    public String getReviewId() {
        return reviewId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public String getRevieweeId() {
        return revieweeId;
    }

    public String getReviewText() {
        return reviewText;
    }
}