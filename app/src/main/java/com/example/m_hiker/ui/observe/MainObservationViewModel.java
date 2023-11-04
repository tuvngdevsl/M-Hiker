package com.example.m_hiker.ui.observe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Observation.ObservationEntity;

import java.util.List;

public class MainObservationViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    AppDatabase db;
    MutableLiveData<List<ObservationEntity>> ObservationList;
    public void setDatabase(AppDatabase db){
        this.db = db;
    }

    public MainObservationViewModel(){
        ObservationList = new MutableLiveData<List<ObservationEntity>>();
    }

    public MutableLiveData<List<ObservationEntity>> getObservationData(){
        List<ObservationEntity> data = db.observationDao().getAllObservation();
        ObservationList.setValue(data);
        return ObservationList;
    }

    public void deleteAllObservation(){
        db.observationDao().deleteAllObservation();
    }

    public void insertObservation(ObservationEntity ob){
        db.observationDao().insertObservation(ob);
    }


    public MutableLiveData<List<ObservationEntity>>  getDataObservation(int hikeId){
        List<ObservationEntity> data = db.observationDao().getHikeById(hikeId);
        ObservationList.setValue(data);
        return ObservationList;

    }
}