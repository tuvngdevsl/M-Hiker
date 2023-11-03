package com.example.m_hiker.Model.Observation;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ObservationDao {
    @Query("SELECT * FROM observation_table WHERE hikeId= :hikeId")
    List<ObservationEntity> getObservationsForHike(int hikeId);

    @Insert
    void insertObservation(ObservationEntity observation);

    @Update
    void updateObservation(ObservationEntity observation);

    @Delete
    void deleteObservation(ObservationEntity observation);
}
