package com.example.pristinecleaningapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PristineCleaningApp.db";
    private static final int DATABASE_VERSION = 1;

    // Tables
    private static final String TABLE_CUSTOMER = "Customer";
    private static final String TABLE_CLEANER = "Cleaner";
    public static final String TABLE_JOB = "Job";
    private static final String TABLE_JOBQUEUE = "JobQueue";
    private static final String TABLE_REVIEW = "Review";

    // CustomerT
    private static final String COL_CUSTOMER_ID = "CustomerID";
    private static final String COL_CUSTOMER_FULL_NAME = "FullName";
    private static final String COL_CUSTOMER_ADDRESS = "Address";
    private static final String COL_CUSTOMER_EMAIL = "Email";
    private static final String COL_CUSTOMER_PASSWORD = "Password";
    private static final String COL_CUSTOMER_PHONE = "PhoneNumber";

    // CleanerT
    private static final String COL_CLEANER_ID = "CleanerID";
    private static final String COL_CLEANER_FULL_NAME = "FullName";
    private static final String COL_CLEANER_EMAIL = "Email";
    private static final String COL_CLEANER_PASSWORD = "Password";
    private static final String COL_CLEANER_PHONE = "PhoneNumber";

    // JobT
    public static final String COL_JOB_ID = "JobID";
    private static final String COL_JOB_CUSTOMER_ID = "CustomerID";
    private static final String COL_JOB_NUM_ROOMS = "NumberOfRooms";
    private static final String COL_JOB_NUM_BATHROOMS = "NumberOfBathrooms";
    private static final String COL_JOB_COMMON_AREA = "CommonAreaIncluded";
    private static final String COL_JOB_TOTAL_SQFT = "TotalSqFt";
    private static final String COL_JOB_CLEANING_TYPE = "CleaningType";
    private static final String COL_JOB_FLOORING_TYPE = "FlooringType";
    private static final String COL_JOB_ESTIMATED_COST = "EstimatedCost";
    public static final String COL_JOB_STATUS = "Status";
    private static final String COL_JOB_IMAGE_URI = "ImageURI";

    // JobQueue
    private static final String COL_JOBQUEUE_JOB_ID = "JobID";
    private static final String COL_JOBQUEUE_CLEANER_ID = "CleanerID";
    private static final String COL_JOBQUEUE_SELECTED = "Selected";

    // Review
    private static final String COL_REVIEW_ID = "ReviewID";
    private static final String COL_REVIEW_REVIEWER_ID = "ReviewerID";
    private static final String COL_REVIEW_REVIEWEE_ID = "RevieweeID";
    private static final String COL_REVIEW_TEXT = "ReviewText";

    private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + "("
            + COL_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_CUSTOMER_FULL_NAME + " TEXT NOT NULL,"
            + COL_CUSTOMER_ADDRESS + " TEXT NOT NULL,"
            + COL_CUSTOMER_EMAIL + " TEXT NOT NULL UNIQUE,"
            + COL_CUSTOMER_PASSWORD + " TEXT NOT NULL,"
            + COL_CUSTOMER_PHONE + " TEXT NOT NULL);";

    private static final String CREATE_CLEANER_TABLE = "CREATE TABLE " + TABLE_CLEANER + "("
            + COL_CLEANER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_CLEANER_FULL_NAME + " TEXT NOT NULL,"
            + COL_CLEANER_EMAIL + " TEXT NOT NULL UNIQUE,"
            + COL_CLEANER_PASSWORD + " TEXT NOT NULL,"
            + COL_CLEANER_PHONE + " TEXT NOT NULL);";

    private static final String CREATE_JOB_TABLE = "CREATE TABLE " + TABLE_JOB + "("
            + COL_JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_JOB_CUSTOMER_ID + " INTEGER NOT NULL,"
            + COL_JOB_NUM_ROOMS + " INTEGER NOT NULL,"
            + COL_JOB_NUM_BATHROOMS + " INTEGER NOT NULL,"
            + COL_JOB_COMMON_AREA + " INTEGER NOT NULL CHECK (" + COL_JOB_COMMON_AREA + " IN (0, 1)),"
            + COL_JOB_TOTAL_SQFT + " REAL NOT NULL,"
            + COL_JOB_CLEANING_TYPE + " TEXT NOT NULL CHECK (" + COL_JOB_CLEANING_TYPE + " IN ('General', 'Heavy')),"
            + COL_JOB_FLOORING_TYPE + " TEXT NOT NULL CHECK (" + COL_JOB_FLOORING_TYPE + " IN ('Carpet', 'Tile', 'Wood')),"
            + COL_JOB_ESTIMATED_COST + " REAL NOT NULL,"
            + COL_JOB_STATUS + " TEXT NOT NULL CHECK (" + COL_JOB_STATUS + " IN ('Pending', 'Accepted', 'Cancelled')),"
            + COL_JOB_IMAGE_URI + " TEXT,"
            + "FOREIGN KEY (" + COL_JOB_CUSTOMER_ID + ") REFERENCES " + TABLE_CUSTOMER + "(" + COL_CUSTOMER_ID + "));";

    private static final String CREATE_JOBQUEUE_TABLE = "CREATE TABLE " + TABLE_JOBQUEUE + "("
            + COL_JOBQUEUE_JOB_ID + " INTEGER NOT NULL,"
            + COL_JOBQUEUE_CLEANER_ID + " INTEGER NOT NULL,"
            + COL_JOBQUEUE_SELECTED + " INTEGER NOT NULL CHECK (" + COL_JOBQUEUE_SELECTED + " IN (0, 1)),"
            + "PRIMARY KEY (" + COL_JOBQUEUE_JOB_ID + ", " + COL_JOBQUEUE_CLEANER_ID + "),"
            + "FOREIGN KEY (" + COL_JOBQUEUE_JOB_ID + ") REFERENCES " + TABLE_JOB + "(" + COL_JOB_ID + "),"
            + "FOREIGN KEY (" + COL_JOBQUEUE_CLEANER_ID + ") REFERENCES " + TABLE_CLEANER + "(" + COL_CLEANER_ID + "));";

    private static final String CREATE_REVIEW_TABLE = "CREATE TABLE " + TABLE_REVIEW + "("
            + COL_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_REVIEW_REVIEWER_ID + " INTEGER NOT NULL,"
            + COL_REVIEW_REVIEWEE_ID + " INTEGER NOT NULL,"
            + COL_REVIEW_TEXT + " TEXT NOT NULL);";
    public static String CQL_JOB_ID = COL_JOB_ID;
    public static String CQL_JOB_STATUS = COL_JOB_STATUS;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_CLEANER_TABLE);
        db.execSQL(CREATE_JOB_TABLE);
        db.execSQL(CREATE_JOBQUEUE_TABLE);
        db.execSQL(CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLEANER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBQUEUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Method - insert a customer
    public long insertCustomer(String fullName, String address, String email, String password, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CUSTOMER_FULL_NAME, fullName);
        values.put(COL_CUSTOMER_ADDRESS, address);
        values.put(COL_CUSTOMER_EMAIL, email);
        values.put(COL_CUSTOMER_PASSWORD, password);
        values.put(COL_CUSTOMER_PHONE, phoneNumber);
        long result = db.insert(TABLE_CUSTOMER, null, values);
        db.close();
        return result;
    }

    // Method - check if a customer exists (for login)
    public boolean checkCustomer(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CUSTOMER,
                new String[]{COL_CUSTOMER_ID},
                COL_CUSTOMER_EMAIL + "=? AND " + COL_CUSTOMER_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Method - update customer details
    public boolean updateCustomer(String customerId, String fullName, String address, String email, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CUSTOMER_FULL_NAME, fullName);
        values.put(COL_CUSTOMER_ADDRESS, address);
        values.put(COL_CUSTOMER_EMAIL, email);
        values.put(COL_CUSTOMER_PASSWORD, password);
        values.put(COL_CUSTOMER_PHONE, phone);
        int rowsAffected = db.update(TABLE_CUSTOMER, values, COL_CUSTOMER_ID + " = ?", new String[]{customerId});
        db.close();
        return rowsAffected > 0;
    }

    // Method - insert a job
    public long insertJob(String customerId, int numRooms, int numBathrooms, int commonAreaIncluded, double totalSqFt, String cleaningType, String flooringType, double estimatedCost, String status, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JOB_CUSTOMER_ID, customerId);
        values.put(COL_JOB_NUM_ROOMS, numRooms);
        values.put(COL_JOB_NUM_BATHROOMS, numBathrooms);
        values.put(COL_JOB_COMMON_AREA, commonAreaIncluded);
        values.put(COL_JOB_TOTAL_SQFT, totalSqFt);
        values.put(COL_JOB_CLEANING_TYPE, cleaningType);
        values.put(COL_JOB_FLOORING_TYPE, flooringType);
        values.put(COL_JOB_ESTIMATED_COST, estimatedCost);
        values.put(COL_JOB_STATUS, status);
        values.put(COL_JOB_IMAGE_URI, imageUri);
        long result = db.insert(TABLE_JOB, null, values);
        db.close();
        return result;
    }

    // Method - get job details by Job ID
    public Job getJobById(long jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JOB,
                new String[]{COL_JOB_ID, COL_JOB_CUSTOMER_ID, COL_JOB_NUM_ROOMS, COL_JOB_NUM_BATHROOMS,
                        COL_JOB_COMMON_AREA, COL_JOB_TOTAL_SQFT, COL_JOB_CLEANING_TYPE, COL_JOB_FLOORING_TYPE,
                        COL_JOB_ESTIMATED_COST, COL_JOB_STATUS, COL_JOB_IMAGE_URI},
                COL_JOB_ID + "=?",
                new String[]{String.valueOf(jobId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Job job = new Job(
                    cursor.getLong(0), // jobId
                    "", // address (not stored in the database, so passes an empty string)
                    cursor.getInt(2), // numRooms
                    cursor.getInt(3), // numBathrooms
                    cursor.getInt(4) == 1, // commonAreaIncluded
                    cursor.getDouble(5), // totalSqFt
                    cursor.getString(6), // cleaningType
                    cursor.getString(7), // flooringType
                    cursor.getDouble(8), // estimatedCost
                    cursor.getString(10), // imageUri
                    cursor.getString(1), // customerId
                    cursor.getString(9) // status
            );
            cursor.close();
            return job;
        }
        return null;
    }

    // Method to fetch cleaners who have applied for a specific job
    public List<String> fetchCleanersForJob(String jobId) {
        List<String> cleanerIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_JOBQUEUE,
                new String[]{COL_JOBQUEUE_CLEANER_ID},
                COL_JOBQUEUE_JOB_ID + "=?",
                new String[]{jobId},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                cleanerIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return cleanerIds;
    }

    // To update the cleaner's "Jobs been selected for" page
    public boolean updateCleanerJobs(String cleanerId, String jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JOBQUEUE_SELECTED, 1);
        int rowsAffected = db.update(
                TABLE_JOBQUEUE,
                values,
                COL_JOBQUEUE_JOB_ID + "=? AND " + COL_JOBQUEUE_CLEANER_ID + "=?",
                new String[]{jobId, cleanerId}
        );
        db.close();
        return rowsAffected > 0;
    }

    // Get the Cleaner ID for a specific job
    public String getCleanerIdForJob(String jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_JOBQUEUE,
                new String[]{COL_JOBQUEUE_CLEANER_ID},
                COL_JOBQUEUE_JOB_ID + "=? AND " + COL_JOBQUEUE_SELECTED + "=1",
                new String[]{jobId},
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            String cleanerId = cursor.getString(0);
            cursor.close();
            return cleanerId;
        }
        return null;
    }

    // Method to delete a job
    public boolean deleteJob(String jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(
                TABLE_JOB,
                COL_JOB_ID + "=?",
                new String[]{jobId}
        );
        db.close();
        return rowsDeleted > 0;
    }

    // Method to insert a cleaner
    public long insertCleaner(String fullName, String email, String password, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CLEANER_FULL_NAME, fullName);
        values.put(COL_CLEANER_EMAIL, email);
        values.put(COL_CLEANER_PASSWORD, password);
        values.put(COL_CLEANER_PHONE, phoneNumber);
        long result = db.insert(TABLE_CLEANER, null, values);
        db.close();
        return result;
    }

    // Method to check if a cleaner exists (for login)
    public boolean checkCleaner(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLEANER,
                new String[]{COL_CLEANER_ID},
                COL_CLEANER_EMAIL + "=? AND " + COL_CLEANER_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Method to update cleaner details
    public boolean updateCleaner(String cleanerId, String fullName, String email, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CLEANER_FULL_NAME, fullName);
        values.put(COL_CLEANER_EMAIL, email);
        values.put(COL_CLEANER_PASSWORD, password);
        values.put(COL_CLEANER_PHONE, phone);
        int rowsAffected = db.update(TABLE_CLEANER, values, COL_CLEANER_ID + " = ?", new String[]{cleanerId});
        db.close();
        return rowsAffected > 0;
    }

    // Method to insert a review
    public long insertReview(String reviewerId, String revieweeId, String reviewText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_REVIEW_REVIEWER_ID, reviewerId);
        values.put(COL_REVIEW_REVIEWEE_ID, revieweeId);
        values.put(COL_REVIEW_TEXT, reviewText);
        long result = db.insert(TABLE_REVIEW, null, values);
        db.close();
        return result;
    }

    // Method to fetch reviews for a specific cleaner
    public List<Review> fetchReviewsForCleaner(String cleanerId) {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_REVIEW,
                new String[]{COL_REVIEW_ID, COL_REVIEW_REVIEWER_ID, COL_REVIEW_TEXT},
                COL_REVIEW_REVIEWEE_ID + "=?",
                new String[]{cleanerId},
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                reviews.add(new Review(
                        cursor.getString(0),
                        cursor.getString(1),
                        cleanerId,
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return reviews;
    }

    // Method to fetch the list of customers who have reviewed the cleanerid
    public List<String> fetchCustomersWhoReviewedCleaner(String cleanerId) {
        List<String> customerIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_REVIEW,
                new String[]{COL_REVIEW_REVIEWER_ID},
                COL_REVIEW_REVIEWEE_ID + "=?",
                new String[]{cleanerId},
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                customerIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return customerIds;
    }

    // Fetch the review for a specific customer
    public String fetchReviewForCustomer(String customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String reviewText = "";
        Cursor cursor = db.query(
                TABLE_REVIEW,
                new String[]{COL_REVIEW_TEXT},
                COL_REVIEW_REVIEWER_ID + "=?",
                new String[]{customerId},
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            reviewText = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return reviewText;
    }

    // Fetch vacant jobs
    public List<Job> fetchVacantJobs() {
        List<Job> vacantJobs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_JOB,
                new String[]{COL_JOB_ID, COL_JOB_CUSTOMER_ID, COL_JOB_NUM_ROOMS, COL_JOB_NUM_BATHROOMS,
                        COL_JOB_COMMON_AREA, COL_JOB_TOTAL_SQFT, COL_JOB_CLEANING_TYPE, COL_JOB_FLOORING_TYPE,
                        COL_JOB_ESTIMATED_COST, COL_JOB_STATUS, COL_JOB_IMAGE_URI},
                COL_JOB_STATUS + "=?",
                new String[]{"Pending"},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                vacantJobs.add(new Job(
                        cursor.getLong(0), // jobId
                        "", // address (not stored in the database, so pass an empty string)
                        cursor.getInt(2), // numRooms
                        cursor.getInt(3), // numBathrooms
                        cursor.getInt(4) == 1, // commonAreaIncluded
                        cursor.getDouble(5), // totalSqFt
                        cursor.getString(6), // cleaningType
                        cursor.getString(7), // flooringType
                        cursor.getDouble(8), // estimatedCost
                        cursor.getString(10), // imageUri
                        cursor.getString(1), // customerId
                        cursor.getString(9) // status
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return vacantJobs;
    }

    // Apply for a job
    public boolean applyForJob(String jobId, String cleanerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JOBQUEUE_JOB_ID, jobId);
        values.put(COL_JOBQUEUE_CLEANER_ID, cleanerId);
        values.put(COL_JOBQUEUE_SELECTED, 0);
        long result = db.insert(TABLE_JOBQUEUE, null, values);
        db.close();
        return result != -1;
    }

    // Fetch jobs for a cleaner
    public List<Job> fetchJobsForCleaner(String cleanerId) {
        List<Job> jobs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_JOBQUEUE,
                new String[]{COL_JOBQUEUE_JOB_ID},
                COL_JOBQUEUE_CLEANER_ID + "=? AND " + COL_JOBQUEUE_SELECTED + "=1",
                new String[]{cleanerId},
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Job job = getJobById(Long.parseLong(cursor.getString(0)));
                if (job != null) {
                    jobs.add(job);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return jobs;
    }
    // Update the job status
    public boolean updateJobStatus(String jobId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JOB_STATUS, status);
        int rowsAffected = db.update(
                TABLE_JOB,
                values,
                COL_JOB_ID + "=?",
                new String[]{jobId}
        );
        db.close();
        return rowsAffected > 0;
    }

    // Get cleaner by ID
    public Cleaner getCleanerById(String cleanerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_CLEANER,
                new String[]{COL_CLEANER_ID, COL_CLEANER_FULL_NAME, COL_CLEANER_EMAIL, COL_CLEANER_PHONE, COL_CLEANER_PASSWORD},
                COL_CLEANER_ID + "=?",
                new String[]{cleanerId},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Cleaner cleaner = new Cleaner(
                    cursor.getString(0), // COL_CLEANER_ID
                    cursor.getString(1), // COL_CLEANER_FULL_NAME
                    cursor.getString(2), // COL_CLEANER_EMAIL
                    cursor.getString(3), // COL_CLEANER_PHONE
                    cursor.getString(4)  // COL_CLEANER_PASSWORD
            );
            cursor.close();
            return cleaner;
        }
        return null;
    }

    // Fetching cleaners who have reviewed a specific customer
    public List<String> fetchCleanersWithReviewedCustomer(String customerId) {
        List<String> cleanerIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_REVIEW,
                new String[]{COL_REVIEW_REVIEWEE_ID},
                COL_REVIEW_REVIEWER_ID + "=?",
                new String[]{customerId},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                cleanerIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return cleanerIds;
    }

    // Fetching cleaners who have reviewed a specific customer
    public List<String> fetchCleanersWhoReviewedCustomer(String customerId) {
        List<String> cleanerIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_REVIEW,
                new String[]{COL_REVIEW_REVIEWEE_ID},
                COL_REVIEW_REVIEWER_ID + "=?",
                new String[]{customerId},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                cleanerIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return cleanerIds;
    }

    // Fetch the review for a specific cleaner
    public String fetchReviewForCleaner(String cleanerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String reviewText = "";
        Cursor cursor = db.query(
                TABLE_REVIEW,
                new String[]{COL_REVIEW_TEXT},
                COL_REVIEW_REVIEWEE_ID + "=?",
                new String[]{cleanerId},
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            reviewText = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return reviewText;
    }

    // Fetch reviews for a specific customer
    public List<Review> fetchReviewsForCustomer(String customerId) {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_REVIEW,
                new String[]{COL_REVIEW_ID, COL_REVIEW_REVIEWER_ID, COL_REVIEW_TEXT},
                COL_REVIEW_REVIEWER_ID + "=?",
                new String[]{customerId},
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            do {
                reviews.add(new Review(
                        cursor.getString(0),
                        customerId,
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return reviews;
    }
}