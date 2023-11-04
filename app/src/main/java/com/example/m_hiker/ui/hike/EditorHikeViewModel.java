package com.example.m_hiker.ui.hike;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Hike.HikeEntity;


import java.util.List;

public class EditorHikeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    AppDatabase db;
    MutableLiveData<HikeEntity> hike = new MutableLiveData<HikeEntity>();

    public void setDatabase(AppDatabase db){
        this.db = db;
    }

    public void getHikeById (int hikeId){
        HikeEntity hikeEntity = db.hikeDao().getHikeById(hikeId);
        hike.postValue(hikeEntity);
    }

    public void updateHike(HikeEntity hikeUpdate){
        db.hikeDao().updateHike(hikeUpdate);
    }

    public void deleteHike(HikeEntity hike){
        db.hikeDao().deleteHikeById(hike.getHikeId());
    }





}