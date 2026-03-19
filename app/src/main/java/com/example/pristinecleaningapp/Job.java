package com.example.pristinecleaningapp;

public class Job {
    private long jobId;
    private String address;
    private int numRooms;
    private int numBathrooms;
    private boolean commonAreaIncluded;
    private double totalSqFt;
    private String cleaningType;
    private String flooringType;
    private double estimatedCost;
    private String imageUri;
    private String customerId; // CustomerId field
    private String status; // Status field

    // Default constructor
    public Job() {
    }

    // Constructor with all fields
    public Job(long jobId, String address, int numRooms, int numBathrooms, boolean commonAreaIncluded,
               double totalSqFt, String cleaningType, String flooringType, double estimatedCost, String imageUri, String customerId, String status) {
        this.jobId = jobId;
        this.address = address;
        this.numRooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.commonAreaIncluded = commonAreaIncluded;
        this.totalSqFt = totalSqFt;
        this.cleaningType = cleaningType;
        this.flooringType = flooringType;
        this.estimatedCost = estimatedCost;
        this.imageUri = imageUri;
        this.customerId = customerId;
        this.status = status;
    }

    // Getters
    public long getJobId() {
        return jobId;
    }

    public String getAddress() {
        return address;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public boolean isCommonAreaIncluded() {
        return commonAreaIncluded;
    }

    public double getTotalSqFt() {
        return totalSqFt;
    }

    public String getCleaningType() {
        return cleaningType;
    }

    public String getFlooringType() {
        return flooringType;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public void setCommonAreaIncluded(boolean commonAreaIncluded) {
        this.commonAreaIncluded = commonAreaIncluded;
    }

    public void setTotalSqFt(double totalSqFt) {
        this.totalSqFt = totalSqFt;
    }

    public void setCleaningType(String cleaningType) {
        this.cleaningType = cleaningType;
    }

    public void setFlooringType(String flooringType) {
        this.flooringType = flooringType;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for easy debugging and logging
    @Override
    public String toString() {
        return "Job{" +
                "jobId=" + jobId +
                ", address='" + address + '\'' +
                ", numRooms=" + numRooms +
                ", numBathrooms=" + numBathrooms +
                ", commonAreaIncluded=" + commonAreaIncluded +
                ", totalSqFt=" + totalSqFt +
                ", cleaningType='" + cleaningType + '\'' +
                ", flooringType='" + flooringType + '\'' +
                ", estimatedCost=" + estimatedCost +
                ", imageUri='" + imageUri + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}