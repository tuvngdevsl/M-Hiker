package com.example.m_hiker.ui.hike;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.Model.Observation.ObservationDao;
import com.example.m_hiker.Model.Observation.ObservationEntity;

import java.util.List;

public class MainHikeViewModel extends ViewModel {
    // View Model is controller
    AppDatabase db;
    MutableLiveData<List<HikeEntity>> hikeList;

    public void setDatabase(AppDatabase db){
        this.db = db;
    }

    public MainHikeViewModel(){
        hikeList = new MutableLiveData<List<HikeEntity>>();
    }

    public MutableLiveData<List<HikeEntity>> getHikeData(){
        List<HikeEntity> data = db.hikeDao().getAllHikes();
        hikeList.setValue(data);
        return hikeList;
    }

    public void deleteAllHike(){
        db.hikeDao().deleteAllHike();
    }


}