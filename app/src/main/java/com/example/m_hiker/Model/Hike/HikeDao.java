package com.example.m_hiker.Model.Hike;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.m_hiker.Model.HikeWithObservations;

import java.util.List;

@Dao
public interface HikeDao {
    @Query("SELECT * FROM hike_table")
    List<HikeEntity> getAllHikes();

    @Query("SELECT * FROM hike_table WHERE hikeId = :id")
    HikeEntity getHikeById(int id);

    @Query("DELETE FROM hike_table WHERE hikeId = :id")
    void deleteHikeById(int id);


    @Transaction
    @Query("SELECT * FROM hike_table WHERE hikeId = :hikeId")
    HikeWithObservations getHikeWithObservations(int hikeId);

    @Query("DELETE FROM HIKE_TABLE")
    void deleteAllHike();

    @Insert
    void insertHike(HikeEntity hike);

    @Update
    void updateHike(HikeEntity hike);

    @Delete
    void deleteHike(HikeEntity hike);


}
