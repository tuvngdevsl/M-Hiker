package com.example.m_hiker.Model.Database;

import android.content.Context;
import androidx.room.Room;

public class RoomHelper {
    public static final String DATABASE_NAME = "mHikerDatabase";

    public static AppDatabase initDatabase(Context context){
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }
}
