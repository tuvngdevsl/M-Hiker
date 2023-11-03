package com.example.m_hiker.Model.Database;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.m_hiker.Model.Hike.HikeDao;
import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.Model.Observation.ObservationDao;
import com.example.m_hiker.Model.Observation.ObservationEntity;

@Database(entities = {
        HikeEntity.class,
        ObservationEntity.class,
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
    public abstract ObservationDao observationDao();
}
