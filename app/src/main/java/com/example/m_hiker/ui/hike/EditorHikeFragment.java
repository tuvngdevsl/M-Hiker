package com.example.m_hiker.ui.hike;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Database.RoomHelper;
import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.R;
import com.example.m_hiker.databinding.FragmentEditorHikeBinding;
import com.example.m_hiker.databinding.FragmentMainBinding;

import java.sql.SQLOutput;
import java.util.Calendar;

public class EditorHikeFragment extends Fragment {
    private EditorHikeViewModel mViewModel;
    int doResult = 0;
    private DatePickerDialog datePickerDialog;
    private AppDatabase db;
    private FragmentEditorHikeBinding binding;

    public static EditorHikeFragment newInstance() {
        return new EditorHikeFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        db = RoomHelper.initDatabase(getContext());
        mViewModel = new ViewModelProvider(this).get(EditorHikeViewModel.class);
        mViewModel.setDatabase(db);

        binding = FragmentEditorHikeBinding.inflate(inflater, container, false);
        //Spinner weather
        Spinner weather = binding.spinnerWeather;
        ArrayAdapter<CharSequence> adapterWeather = ArrayAdapter.createFromResource(getActivity(),
                R.array.weather_array, android.R.layout.simple_spinner_item);
        adapterWeather.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weather.setAdapter(adapterWeather);
        //Spinner difficulty
        Spinner levelDifficulty = binding.spinnerDifficulty;
        ArrayAdapter<CharSequence> adapterDifficulty = ArrayAdapter.createFromResource(getActivity(),
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelDifficulty.setAdapter(adapterDifficulty);
        //DatePicker
        binding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        binding.editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        //check radio
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton_yes) {
                    doResult = 1;
                } else {
                    doResult = 0;
                }
            }
        });



        //getAgurment tu fragment
        int hikeId = getArguments().getInt("hikeId"); //getArguments lay(nhan) du lieu tu mainFragment key phai trung voi key ben fragment
        mViewModel.hike.observe(
                getViewLifecycleOwner(),
                he -> {
                    binding.editTextNameHike.setText(he.getNameHike());
                    binding.editTextLocation.setText(he.getLocation());
                    binding.editTextDate.setText(he.getDateOfHike());
                    binding.radioGroup.check(he.getParkingAvailable() == 1 ? binding.radioButtonYes.getId() : binding.radioButtonNo.getId());
                    binding.editTextLength.setText(he.getLengthTheHike());
                    binding.spinnerDifficulty.setSelection(adapterDifficulty.getPosition(he.getDifficulty()));
                    binding.editTextDescription.setText(he.getDescription());
                    binding.spinnerWeather.setSelection(adapterWeather.getPosition(he.getWeather()));
                    binding.editTextEstimateTime.setText(he.getEstimatedTime());
                }
        );

        mViewModel.getHikeById(hikeId);  // trả về một đối tượng LiveData chứa thông tin về một "hike" dựa trên ID đã được cung cấp (hikeId);

        //Save detail hike
        binding.buttonSave.setOnClickListener(v -> this.handleSave(hikeId));


        //Button observation
        binding.buttonObservation.setOnClickListener(v -> this.handleObservation(hikeId));

        View root = binding.getRoot();
        return root;
    }

    private void handleObservation(int hikeId) {
        Bundle bundle = new Bundle();
        bundle.putInt("hikeId", hikeId);

        Navigation.findNavController(getView()).navigate(R.id.action_editorHikeFragment_to_mainObservationFragment, bundle);
    }

    private void handleSave(int hikeId) {
        if (binding.editTextNameHike.length() == 0) {
            showError(binding.editTextNameHike, "Name of Hike is Required");
        }
        if (binding.editTextLocation.length() == 0) {
            showError(binding.editTextLocation, "Location is Required");
        }
        if (binding.editTextDate.length() == 0) {
            showError(binding.editTextDate, "Date of the hike is Required");
        }
        if (binding.editTextLength.length() == 0) {
            showError(binding.editTextLength, "Length the hike is Required");
        }

        if (binding.spinnerDifficulty.getSelectedItemPosition()== 0) {
            binding.spinnerDifficulty.requestFocus();
            ((TextView) binding.spinnerDifficulty.getSelectedView()).setError("Please choose another level of difficulty!.");
        }

        if (binding.spinnerWeather.getSelectedItemPosition() == 0) {
            binding.spinnerWeather.requestFocus();
            ((TextView) binding.spinnerWeather.getSelectedView()).setError("Please choose another weather!.");
        }

        if (binding.editTextEstimateTime.length() == 0) {
            showError(binding.editTextEstimateTime, "Estimate Time is required!");
        }

        if (binding.editTextNameHike.length() > 0 &&
                binding.editTextLocation.length() > 0 &&
                binding.editTextDate.length() > 0 &&
                binding.editTextLength.length() > 0 &&
                binding.spinnerDifficulty.getSelectedItemPosition() != 0 &&
                binding.spinnerWeather.getSelectedItemPosition() != 0 &&
                binding.editTextEstimateTime.length() > 0) {
            String name = binding.editTextNameHike.getText().toString();
            String location = binding.editTextLocation.getText().toString();
            String date = binding.editTextDate.getText().toString();
            int parkingAvailable = binding.radioGroup.getCheckedRadioButtonId() == binding.radioButtonYes.getId() ? 1 : 0;
            String length = binding.editTextLength.getText().toString();
            String difficulty = binding.spinnerDifficulty.getSelectedItem().toString();
            String description = binding.editTextDescription.getText().toString();
            String weather = binding.spinnerWeather.getSelectedItem().toString();
            String estimateTime = binding.editTextEstimateTime.getText().toString();

            HikeEntity updateHike = new HikeEntity(name, location, date, parkingAvailable, length, difficulty, description, weather, estimateTime);
            updateHike.setHikeId(hikeId);
            showConfirmationDialog(updateHike);


        }
    }

    private void showError(EditText editText, String errorMessage) {
        editText.requestFocus();
        editText.setError(errorMessage);
    }

    private void showConfirmationDialog(HikeEntity hike){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        builder.setIcon(R.drawable.baseline_check_circle_outline_24);
        builder.setMessage("Are these details correct? \n" +
                "Hike Name: " + hike.getNameHike() + "\n" +
                "Location: " + hike.getLocation() + "\n" +
                "Date: " + hike.getDateOfHike() + "\n" +
                "Length: " + hike.getLengthTheHike() + "\n" +
                "Parking available: " + hike.getParkingAvailable() + '\n' +
                "Difficulty: " + hike.getDifficulty() + "\n" +
                "Description: " + hike.getDescription() + "\n" +
                "Weather: " + hike.getWeather() + "\n" +
                "Estimated Time: " + hike.getEstimatedTime());
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                mViewModel.updateHike(hike);
                Navigation.findNavController(getView()).navigateUp();
                Toast.makeText(getContext(), "Update Hike Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Make Changes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Temp data



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
       inflater.inflate(R.menu.menu_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.action_delete:
               return deleteAndReturn();
           default: return super.onContextItemSelected(item);
       }

    }

    private boolean deleteAndReturn() {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete")
                .setMessage("Are you sure to delete trip?!!")
                .setNegativeButton("No", null)
                .setNeutralButton("Yes", (dialog, delete) -> {
                    int hikeId = getArguments().getInt("hikeId");
                    HikeEntity hike = db.hikeDao().getHikeById(hikeId);
                    mViewModel.deleteHike(hike);
                    Toast.makeText(getActivity(), "Delete Hike Successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigateUp();
                }).show();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}