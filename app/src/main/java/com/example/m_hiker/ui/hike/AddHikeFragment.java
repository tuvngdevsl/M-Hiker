package com.example.m_hiker.ui.hike;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
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
import com.example.m_hiker.databinding.FragmentAddHikeBinding;

import java.util.Calendar;

public class AddHikeFragment extends Fragment {

    private DatePickerDialog datePickerDialog;
    private FragmentAddHikeBinding binding;
    private AppDatabase db;
    private int doResult = 1;

    public static AddHikeFragment newInstance() {
        return new AddHikeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddHikeBinding.inflate(inflater, container, false);
        db = RoomHelper.initDatabase(getContext());


        // Dropdown
        Spinner weather = binding.spinnerWeather;
        ArrayAdapter<CharSequence> adapterWeather = ArrayAdapter.createFromResource(getActivity(),
                R.array.weather_array, android.R.layout.simple_spinner_item);
        adapterWeather.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weather.setAdapter(adapterWeather);

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
        CalendarExpense();

        // Check radio Button
        binding.radioGroup.check(R.id.radioButton_yes);
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
        // Save Hike Button
        binding.buttonSave.setOnClickListener(v -> this.handleSave());


        View root = binding.getRoot();
        return root;
    }


    private void handleSave() {
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
            String length = binding.editTextLength.getText().toString();
            String difficulty = binding.spinnerDifficulty.getSelectedItem().toString();
            String description = binding.editTextDescription.getText().toString();
            String weather = binding.spinnerWeather.getSelectedItem().toString();
            String estimateTime = binding.editTextEstimateTime.getText().toString();

            HikeEntity hike = new HikeEntity(name, location, date, doResult, length, difficulty, description, weather, estimateTime);
            showConfirmationDialog(hike);


        }


    }

    private void showError(EditText editText, String errorMessage) {
        editText.requestFocus();
        editText.setError(errorMessage);
    }

    private void CalendarExpense() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        binding.editTextDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
    }

    HikeEntity currentHike;
    private void showConfirmationDialog(HikeEntity hike){
        currentHike = hike;
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

                db.hikeDao().insertHike(hike);
                Navigation.findNavController(getView()).navigate(R.id.mainFragment);
                Toast.makeText(getContext(), "Add Hike Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Make Changes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                fillFields(currentHike);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Temp data
    private void fillFields(HikeEntity hike){
        if(hike != null){
            binding.editTextNameHike.setText(currentHike.getNameHike());
            binding.editTextLocation.setText(currentHike.getLocation());
            binding.editTextDate.setText(currentHike.getDateOfHike());
            binding.editTextLength.setText(currentHike.getLengthTheHike());

            String difficulty = currentHike.getDifficulty();
            ArrayAdapter adapterDifficulty = (ArrayAdapter) binding.spinnerDifficulty.getAdapter();
            int spinnerDifficultyPosition = adapterDifficulty.getPosition(difficulty);
            binding.spinnerDifficulty.setSelection(spinnerDifficultyPosition);

            binding.editTextDescription.setText(currentHike.getDescription());

            String weather = currentHike.getWeather();
            ArrayAdapter adapter = (ArrayAdapter) binding.spinnerWeather.getAdapter();
            int spinnerPosition = adapter.getPosition(weather);
            binding.spinnerWeather.setSelection(spinnerPosition);
            binding.editTextEstimateTime.setText(currentHike.getEstimatedTime());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}