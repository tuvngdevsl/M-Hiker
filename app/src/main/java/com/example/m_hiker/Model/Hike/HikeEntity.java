package com.example.m_hiker.Model.Hike;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "hike_table")
public class HikeEntity {
    @PrimaryKey(autoGenerate = true)
    private int hikeId;
    private String nameHike;
    private String location;
    private String dateOfHike;
    private int parkingAvailable;
    private String lengthTheHike;
    private String difficulty;
    private String description;
    private String weather;
    private String estimatedTime;

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
    }

    public String getNameHike() {
        return nameHike;
    }

    public void setNameHike(String nameHike) {
        this.nameHike = nameHike;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateOfHike() {
        return dateOfHike;
    }

    public void setDateOfHike(String dateOfHike) {
        this.dateOfHike = dateOfHike;
    }

    public int getParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(int parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    public String getLengthTheHike() {
        return lengthTheHike;
    }

    public void setLengthTheHike(String lengthTheHike) {
        this.lengthTheHike = lengthTheHike;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    // Validate function
    public void validate() throws Exception {
        if (nameHike.isEmpty() || location.isEmpty() || dateOfHike.isEmpty() || parkingAvailable == 2 || lengthTheHike.isEmpty() || weather.isEmpty() || estimatedTime.isEmpty()) {
            throw new Exception("Please fill in all required fields");
        }
    }

    public HikeEntity(){

    }

    public HikeEntity(String nameHike, String location, String dateOfHike, int parkingAvailable, String lengthTheHike, String difficulty, String description, String weather, String estimatedTime) {
        this.nameHike = nameHike;
        this.location = location;
        this.dateOfHike = dateOfHike;
        this.parkingAvailable = parkingAvailable;
        this.lengthTheHike = lengthTheHike;
        this.difficulty = difficulty;
        this.description = description;
        this.weather = weather;
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {
        return "Name: " + nameHike + "\n" +
                "Location: " + location + "\n" +
                "Date: " + dateOfHike + "\n" +
                "Parking Available: " + parkingAvailable + "\n" +
                "Length: " + lengthTheHike + "\n" +
                "Difficulty: " + difficulty + "\n" +
                "Description: " + description + "\n" +
                "Custom Field 1: " + estimatedTime + "\n" +
                "Weather: " + weather;
    }
}
