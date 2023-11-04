package com.example.m_hiker.ui.observe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Observation.ObservationEntity;

import java.util.List;

public class AddObservationViewModel extends ViewModel {
    AppDatabase db;
    MutableLiveData<List<ObservationEntity>> ObservationList;
    public void setDatabase(AppDatabase db){
        this.db = db;
    }

    public AddObservationViewModel(){
        ObservationList = new MutableLiveData<List<ObservationEntity>>();
    }

    public void insertObservation(ObservationEntity ob){
        db.observationDao().insertObservation(ob);
    }
}