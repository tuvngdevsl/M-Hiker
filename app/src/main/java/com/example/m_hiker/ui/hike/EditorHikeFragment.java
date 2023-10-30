package com.example.m_hiker.ui.hike;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.m_hiker.R;
import com.example.m_hiker.databinding.FragmentEditorHikeBinding;
import com.example.m_hiker.databinding.FragmentMainBinding;

public class EditorHikeFragment extends Fragment {

    private EditorHikeViewModel mViewModel;
    private FragmentEditorHikeBinding binding;

    public static EditorHikeFragment newInstance() {
        return new EditorHikeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel = new ViewModelProvider(this).get(EditorHikeViewModel.class);

        binding = FragmentEditorHikeBinding.inflate(inflater, container, false);

        //getAgurment tu fragment
        String hikeId = getArguments().getString("hikeId"); //getArguments lay(nhan) du lieu tu mainFragment key phai trung voi key ben fragment

        binding.editTextNameHike.setText(hikeId);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}