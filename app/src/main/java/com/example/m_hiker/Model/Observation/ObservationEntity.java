package com.example.m_hiker.Model.Observation;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.example.m_hiker.Model.Hike.HikeEntity;


@Entity(tableName = "observation_table")
public class ObservationEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull private int observationId;

    @Embedded
    private HikeEntity hikeEntity;

    private String observation;
    private String timeOfObservation;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    private String photoPath;


    private String additionalComments;


    public HikeEntity getHikeEntity() {
        return hikeEntity;
    }

    public void setHikeEntity(HikeEntity hikeEntity) {
        this.hikeEntity = hikeEntity;
    }
    public int getObservationId() {
        return observationId;
    }

    public void setObservationId(int observationId) {
        this.observationId = observationId;
    }


    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getTimeOfObservation() {
        return timeOfObservation;
    }

    public void setTimeOfObservation(String timeOfObservation) {
        this.timeOfObservation = timeOfObservation;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }

    public ObservationEntity(){

    }
    public ObservationEntity(HikeEntity hikeEntity, String observation, String timeOfObservation, String additionalComments) {
        this.hikeEntity = hikeEntity;
        this.observation = observation;
        this.timeOfObservation = timeOfObservation;
        this.additionalComments = additionalComments;
    }


    public void validate() throws Exception {
        if (observation.isEmpty() || timeOfObservation == null || additionalComments.isEmpty()) {
            throw new Exception("Please fill in all required fields");
        }
    }


}
