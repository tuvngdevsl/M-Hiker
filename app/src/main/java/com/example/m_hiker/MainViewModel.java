package com.example.m_hiker;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.m_hiker.Model.HikeEntity;
import com.example.m_hiker.Model.SampleDataProvider;

import java.util.List;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    // View Model is controller
    MutableLiveData<List<HikeEntity>> hikeList = new MutableLiveData<List<HikeEntity>>();

    {
        hikeList.setValue(SampleDataProvider.getHikes());
    }
}