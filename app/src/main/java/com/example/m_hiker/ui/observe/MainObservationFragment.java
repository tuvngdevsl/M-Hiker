package com.example.m_hiker.ui.observe;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.m_hiker.Adapter.ObservationListAdapter;
import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Database.RoomHelper;
import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.Model.Observation.ObservationEntity;
import com.example.m_hiker.R;
import com.example.m_hiker.databinding.FragmentMainObservationBinding;

import java.util.Optional;

public class MainObservationFragment extends Fragment implements ObservationListAdapter.ListItemListener{

    private MainObservationViewModel mViewModel;
    private FragmentMainObservationBinding binding;
    private ObservationListAdapter adapter;
    private AppDatabase db;


    public static MainObservationFragment newInstance() {
        return new MainObservationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MainObservationViewModel.class);
        binding = FragmentMainObservationBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

        db = RoomHelper.initDatabase(getContext());
        mViewModel.setDatabase(db);
        int hikeId = getArguments().getInt("hikeId");
        //RecyclerView
        RecyclerView rv = binding.recyclerview;
        rv.setHasFixedSize(true); // each row has eqaul size regardless of its content
        rv.addItemDecoration(new DividerItemDecoration(
                getContext(),
                (new LinearLayoutManager(getContext())).getOrientation()
        ));

        mViewModel.getDataObservation(hikeId).observe(getViewLifecycleOwner(),
                observationEntities -> {
                    adapter = new ObservationListAdapter(observationEntities, this);
                    binding.recyclerview.setAdapter(adapter);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                });


        binding.fabAddObservation.setOnClickListener(v -> handleAddObservation());

        View root = binding.getRoot();
        return root;
    }

    public void onItemClick(int observationId) {
        Optional<ObservationEntity> observation = mViewModel.ObservationList.getValue().stream().filter(o -> o.getObservationId() == observationId).findFirst();

        if (observation.isPresent()) {
            Bundle bundle = new Bundle();   // Bundle la hashmap trong android dung de chuyen du lieu giua 2 fragment
            bundle.putInt("observationId", observationId);
            Navigation.findNavController(getView())
                    .navigate(R.id.action_mainObservationFragment_to_editorObservationFragment, bundle);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.observation_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete: return deleteAllObservation();
            case R.id.action_home: return backToHome();
            default:  return super.onOptionsItemSelected(item);
        }

    }

    private boolean backToHome() {
        Navigation.findNavController(getView()).navigate(R.id.mainFragment);
        return true;
    }

    private boolean deleteAllObservation() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure to delete all Observation?!!")
                .setNegativeButton("No", null)
                .setNeutralButton("Yes", (dialog, delete) -> {
                    mViewModel.deleteAllObservation();
                    Toast.makeText(getActivity(), "Delete all Observation successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigateUp();
                }).show();
        return true;
    }

    private void handleAddObservation() {
        Bundle bundle = new Bundle();
        int hikeId = getArguments().getInt("hikeId");
        bundle.putInt("hikeId", hikeId);
        Navigation.findNavController(getView()).navigate(R.id.action_mainObservationFragment_to_addObservationFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}