package com.example.m_hiker.Model.Observation;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.m_hiker.Model.Constants;
import com.example.m_hiker.Model.Hike.HikeEntity;

import java.util.Date;

@Entity(tableName = "observation_table",
        foreignKeys = @ForeignKey(entity = HikeEntity.class,
                parentColumns = "hikeId",
                childColumns = "hikeId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("hikeId")})
public class ObservationEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull private int observationId;
    private int hikeId;

    private String observation;
    private String timeOfObservation;
    private String additionalComments;

    public int getObservationId() {
        return observationId;
    }

    public void setObservationId(int observationId) {
        this.observationId = observationId;
    }

    public int getHikeId() {
        return hikeId;
    }

    public void setHikeId(int hikeId) {
        this.hikeId = hikeId;
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


    public ObservationEntity(int hikeId, String observation, String timeOfObservation, String additionalComments) {
        this.hikeId = hikeId;
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
