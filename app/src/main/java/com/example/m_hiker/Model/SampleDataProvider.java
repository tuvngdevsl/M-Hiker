package com.example.m_hiker.Model;

import java.util.ArrayList;
import java.util.List;

public class SampleDataProvider {
    public static List<HikeEntity> getHikes() {
        List<HikeEntity> hikes = new ArrayList<>();

        hikes.add(new HikeEntity(
                "1", // id
                "Snowdon", // nameHike
                "Wales", // location
                "2022-08-15", // dateOfHike
                true, // parkingAvailable
                "10 miles", // lengthTheHike
                "Moderate", // difficulty
                "A beautiful hike in the mountains.", // description
                "Sunny", // weather
                "5 hours" // estimatedTime
        ));
        hikes.add(new HikeEntity(
                "2", // id
                "Trosley Country Park", // nameHike
                "England", // location
                "2022-09-01", // dateOfHike
                false, // parkingAvailable
                "3 miles", // lengthTheHike
                "Easy", // difficulty
                "A relaxing walk in the park.", // description
                "Cloudy", // weather
                "1 hour" // estimatedTime
        ));
        hikes.add(new HikeEntity(
                "3", // id
                "Snowdon", // nameHike
                "Wales", // location
                "2022-08-15", // dateOfHike
                true, // parkingAvailable
                "10 miles", // lengthTheHike
                "Moderate", // difficulty
                "A beautiful hike in the mountains.", // description
                "Sunny", // weather
                "5 hours" // estimatedTime
        ));
        hikes.add(new HikeEntity(
                "4", // id
                "Snowdon", // nameHike
                "Wales", // location
                "2022-08-15", // dateOfHike
                true, // parkingAvailable
                "10 miles", // lengthTheHike
                "Moderate", // difficulty
                "A beautiful hike in the mountains.", // description
                "Sunny", // weather
                "5 hours" // estimatedTime
        ));


        return hikes;
    }
}
