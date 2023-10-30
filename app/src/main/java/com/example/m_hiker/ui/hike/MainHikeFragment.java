package com.example.m_hiker.ui.hike;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m_hiker.Adapter.HikeListAdapter;
import com.example.m_hiker.Model.HikeEntity;
import com.example.m_hiker.Model.SampleDataProvider;
import com.example.m_hiker.databinding.FragmentMainBinding;

public class MainHikeFragment extends Fragment {

    private MainHikeViewModel mViewModel;   // View Model is controller
    private FragmentMainBinding binding;
    private HikeListAdapter adapter;

    public static MainHikeFragment newInstance() {
        return new MainHikeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(MainHikeViewModel.class);
        binding = FragmentMainBinding.inflate(inflater, container, false);

        HikeEntity h = SampleDataProvider.getHikes().get(0);

        RecyclerView rv = binding.recyclerview;
        rv.setHasFixedSize(true); // each row has eqaul size regardless of its content
        rv.addItemDecoration(new DividerItemDecoration(
                getContext(),
                (new LinearLayoutManager(getContext())).getOrientation()
        ));

        mViewModel.hikeList.observe(
                getViewLifecycleOwner(),
                hikeEntities -> {
//                    System.out.println("#Hike: " + hikeEntities.size());
                    adapter = new HikeListAdapter(hikeEntities);
                    binding.recyclerview.setAdapter(adapter);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
        );



        View root = binding.getRoot();




        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}