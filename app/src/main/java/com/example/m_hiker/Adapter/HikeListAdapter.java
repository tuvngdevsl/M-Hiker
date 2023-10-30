package com.example.m_hiker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.Model.HikeEntity;
import com.example.m_hiker.R;
import com.example.m_hiker.databinding.ListItemBinding;

import java.util.List;

public class HikeListAdapter extends RecyclerView.Adapter<HikeListAdapter.HikeViewHolder> {

    public class HikeViewHolder extends RecyclerView.ViewHolder{ //View holder is a view of each list item in recycler view

        private final ListItemBinding itemViewBinding; // control list_item.xml to list item binding

        public HikeViewHolder(View itemView) { // Hike View Holder Chứa cái UI list item.
            super(itemView);
            this.itemViewBinding = ListItemBinding.bind(itemView);
        }

        public void bindData(HikeEntity hData){
            itemViewBinding.hikeName.setText(hData.getNameHike());
            itemViewBinding.hikeDay.setText(hData.getDateOfHike());

        }
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   // display each of hike in recyclerview
        View view = LayoutInflater.from(parent.getContext())                              // Tại mỗi dòng có view nào được hiển thị
                .inflate(R.layout.list_item, parent, false);                   // Tạo view từ R.layout.listitem.xml

        return new HikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) { // hàm để đưa dữ liệu đưa vào onBindViewHolder, Và position của từng dòng
        HikeEntity hData = hikeList.get(position);
        holder.bindData(hData);
    }

    @Override
    public int getItemCount() {
        return hikeList.size();
    }   // Nói trước cho recycler view bao nhiêu dòng của list hike được hiển thị



    private List<HikeEntity> hikeList;
    public HikeListAdapter(List<HikeEntity> hikeList){
        this.hikeList = hikeList;
    }


}
