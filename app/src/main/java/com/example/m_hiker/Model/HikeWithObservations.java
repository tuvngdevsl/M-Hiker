package com.example.m_hiker.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.Model.Observation.ObservationEntity;

import java.util.List;

public class HikeWithObservations {
    @Embedded
    public HikeEntity hike;
    @Relation(
            parentColumn = "hikeId",
            entityColumn = "hikeId"
    )
    public List<ObservationEntity> observations;
}