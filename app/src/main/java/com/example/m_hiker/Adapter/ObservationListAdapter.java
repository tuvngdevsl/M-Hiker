package com.example.m_hiker.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.Model.Observation.ObservationEntity;
import com.example.m_hiker.R;
import com.example.m_hiker.databinding.ListItemBinding;
import com.example.m_hiker.databinding.ListObservationItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ObservationListAdapter extends RecyclerView.Adapter<ObservationListAdapter.ObservationViewHolder>{


    private List<ObservationEntity> observationList;
    private ListItemListener listener;

    public ObservationListAdapter(List<ObservationEntity> observationList, ListItemListener listener) {
        this.observationList = observationList;
        this.listener = listener;

    }

    public interface ListItemListener {
        void onItemClick(int observationId);
    }
    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_observation_item, parent, false);
        return new ObservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        ObservationEntity oData = observationList.get(position);
        holder.bindData(oData);
    }

    @Override
    public int getItemCount() {
        return observationList.size();
    }



    public class ObservationViewHolder extends  RecyclerView.ViewHolder{
       private ListObservationItemBinding itemViewBinding;
       public ObservationViewHolder(@NonNull View itemView) {
           super(itemView);
           this.itemViewBinding = ListObservationItemBinding.bind(itemView);
       }

       public void bindData(ObservationEntity oData) {
           itemViewBinding.ObservationName.setText(oData.getObservation());
           itemViewBinding.ObservationTime.setText(oData.getTimeOfObservation());
           itemViewBinding.getRoot().setOnClickListener(v -> listener.onItemClick(oData.getObservationId()));
       }
   }
}
