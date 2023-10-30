package com.example.m_hiker.Model;

import com.example.m_hiker.Constants;

public class HikeEntity {

    private String id;
    private String nameHike;
    private String location;
    private String dateOfHike;
    private Boolean parkingAvailable;
    private String lengthTheHike;
    private String difficulty;
    private String description;
    private String weather;
    private String estimatedTime;


    // Validate function
    public void validate() throws Exception {
        if (nameHike.isEmpty() || location.isEmpty() || dateOfHike.isEmpty() || parkingAvailable == null || lengthTheHike.isEmpty() || weather.isEmpty() || estimatedTime.isEmpty()) {
            throw new Exception("Please fill in all required fields");
        }
    }

    public HikeEntity() {
        this(
                Constants.NEW_HIKE_ID,
                Constants.EMPTY_STRING,
                Constants.EMPTY_STRING,
                Constants.EMPTY_STRING,
                null,
                Constants.EMPTY_STRING,
                Constants.EMPTY_STRING,
                Constants.EMPTY_STRING,
                Constants.EMPTY_STRING,
                Constants.EMPTY_STRING
        );
    }

    public HikeEntity(String nameHike,
                      String location,
                      String dateOfHike,
                      Boolean parkingAvailable,
                      String lengthTheHike,
                      String difficulty,
                      String description,
                      String weather,
                      String estimatedTime) {
        this(Constants.NEW_HIKE_ID, nameHike, location, dateOfHike, parkingAvailable, lengthTheHike, difficulty, description, weather, estimatedTime);
    }

    public HikeEntity(String id,
                      String nameHike,
                      String location,
                      String dateOfHike,
                      Boolean parkingAvailable,
                      String lengthTheHike,
                      String difficulty,
                      String description,
                      String weather,
                      String estimatedTime) {
        setId(id);
        setNameHike(nameHike);
        setLocation(location);
        setDateOfHike(dateOfHike);
        setParkingAvailable(parkingAvailable);
        setLengthTheHike(lengthTheHike);
        setDifficulty(difficulty);
        setDescription(description);
        setWeather(weather);
        setEstimatedTime(estimatedTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(Boolean parkingAvailable) {
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
