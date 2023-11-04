package com.example.m_hiker.ui.hike;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.m_hiker.Adapter.HikeListAdapter;
import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Database.RoomHelper;
import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.R;
import com.example.m_hiker.databinding.FragmentMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainHikeFragment extends Fragment implements HikeListAdapter.ListItemListener {

    private MainHikeViewModel mViewModel;   // View Model is controller
    private SearchView searchView;
    private FragmentMainBinding binding;
    private HikeListAdapter adapter;

    private AppDatabase db;

    public static MainHikeFragment newInstance() {
        return new MainHikeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        db = RoomHelper.initDatabase(getContext());

        mViewModel = new ViewModelProvider(this).get(MainHikeViewModel.class);
        mViewModel.setDatabase(db);
        binding = FragmentMainBinding.inflate(inflater, container, false);

        //RecyclerView
        RecyclerView rv = binding.recyclerview;
        rv.setHasFixedSize(true); // each row has eqaul size regardless of its content
        rv.addItemDecoration(new DividerItemDecoration(
                getContext(),
                (new LinearLayoutManager(getContext())).getOrientation()
        ));

        // get Data from Database
        mViewModel.getHikeData().observe(
                getViewLifecycleOwner(),
                hikeEntities -> {
                    adapter = new HikeListAdapter(hikeEntities, this);
                    binding.recyclerview.setAdapter(adapter);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
        );

        View root = binding.getRoot();


        binding.fabAddHike.setOnClickListener(v -> this.handleClick());
        return root;

    }


    @Override
    public void onItemClick(int hikeId) {
        Optional<HikeEntity> hike = mViewModel.hikeList.getValue().stream().filter(h -> h.getHikeId() == hikeId).findFirst();

        if (hike.isPresent()) {
            Bundle bundle = new Bundle();   // Bundle la hashmap trong android dung de chuyen du lieu giua 2 fragment
            bundle.putInt("hikeId", hikeId);
            Navigation.findNavController(getView())
                    .navigate(R.id.action_mainFragment_to_editorHikeFragment, bundle);
        }
    }


    public void handleClick() {
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_addHikeFragment);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                return deleteAndReturn();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean deleteAndReturn() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure to delete all hike?!!")
                .setNegativeButton("No", null)
                .setNeutralButton("Yes", (dialog, delete) -> {
                    mViewModel.deleteAllHike();
                    Toast.makeText(getActivity(), "Delete all Hike successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.mainFragment);
                }).show();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}