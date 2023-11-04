package com.example.m_hiker.Model.Observation;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ObservationDao {

    @Insert
    void insertObservation(ObservationEntity observation);

    @Update
    void updateObservation(ObservationEntity observation);

    @Delete
    void deleteObservation(ObservationEntity observation);


    @Query("DELETE FROM observation_table WHERE observationId = :id")
    void deleteById(int id);
    @Query("SELECT * FROM observation_table")
    List<ObservationEntity> getAllObservation();

    @Query("SELECT * FROM observation_table WHERE hikeId = :hikeId")
    List<ObservationEntity> getHikeById(int hikeId);

    @Query("SELECT * FROM observation_table WHERE observationId = :id")
    ObservationEntity getObservationById(int id);

    @Query("DELETE FROM observation_table")
    void deleteAllObservation();


}
