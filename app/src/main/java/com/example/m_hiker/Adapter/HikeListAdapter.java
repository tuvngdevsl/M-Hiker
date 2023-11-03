package com.example.m_hiker.Adapter;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.R;
import com.example.m_hiker.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.os.Handler;

public class HikeListAdapter extends RecyclerView.Adapter<HikeListAdapter.HikeViewHolder> implements Filterable {
    private List<HikeEntity> hikeList;
    private List<HikeEntity> hikeListFull;
    private ListItemListener listener;

    public HikeListAdapter(List<HikeEntity> hikeList, ListItemListener listener) {
        this.hikeList = hikeList;
        this.listener = listener;
        this.hikeListFull = new ArrayList<>(hikeList);
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new HikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        HikeEntity hData = hikeList.get(position);
        holder.bindData(hData);
    }

    @Override
    public int getItemCount() {
        return hikeList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<HikeEntity> filteredList = new ArrayList<>();
                if(constraint.toString().isEmpty()){
                    filteredList.addAll(hikeListFull);
                }else{
                    String filter = constraint.toString().toLowerCase().trim();
                    for (HikeEntity item : hikeListFull) {
                        if (item.getNameHike().toLowerCase().contains(filter)) {
                            filteredList.add(item);
                        } else if (item.getLocation().toLowerCase().contains(filter)) {
                            filteredList.add(item);
                        } else if (item.getDateOfHike().toLowerCase().contains(filter)) {
                            filteredList.add(item);
                        } else if (item.getLengthTheHike().toLowerCase().contains(filter)) {
                            filteredList.add(item);
                        }
                    }
                }


                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        hikeList.clear();
                        hikeList.addAll((Collection<? extends HikeEntity>) results.values);
                        notifyDataSetChanged();
                    }
                });
            }
        };
    }

    public interface ListItemListener {
        void onItemClick(int hikeId);
    }

    public class HikeViewHolder extends RecyclerView.ViewHolder {
        private final ListItemBinding itemViewBinding;

        public HikeViewHolder(View itemView) {
            super(itemView);
            this.itemViewBinding = ListItemBinding.bind(itemView);
        }

        public void bindData(HikeEntity hData) {
            itemViewBinding.hikeName.setText(hData.getNameHike());
            itemViewBinding.hikeDay.setText(hData.getDateOfHike());
            itemViewBinding.hikeLength.setText(hData.getLengthTheHike());
            itemViewBinding.hikeLocation.setText(hData.getLocation());
            itemViewBinding.getRoot().setOnClickListener(v -> listener.onItemClick(hData.getHikeId()));
        }
    }
}