package com.example.m_hiker.ui.observe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Observation.ObservationEntity;

import java.util.List;

public class EditorObservationViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    AppDatabase db;
    MutableLiveData<ObservationEntity> observation;
    public void setDatabase(AppDatabase db){
        this.db = db;
    }

    public EditorObservationViewModel(){
        observation = new MutableLiveData<ObservationEntity>();
    }

    public void getObservationById(int observationId){
        ObservationEntity observationEntity = db.observationDao().getObservationById(observationId);
        observation.postValue(observationEntity);
    }

    public void updateObservation(ObservationEntity observationUpdate){
        db.observationDao().updateObservation(observationUpdate);

    }
}