package com.example.m_hiker.ui.observe;


import static android.app.Activity.RESULT_OK;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.example.m_hiker.Model.Database.AppDatabase;
import com.example.m_hiker.Model.Database.RoomHelper;
import com.example.m_hiker.Model.Hike.HikeEntity;
import com.example.m_hiker.Model.Observation.ObservationEntity;
import com.example.m_hiker.R;
import com.example.m_hiker.databinding.FragmentAddObservationBinding;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddObservationFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private AddObservationViewModel mViewModel;
    private FragmentAddObservationBinding binding;
    private AppDatabase db;
    private DatePickerDialog datePickerDialog;

    private ObservationEntity ob = new ObservationEntity();



    public static AddObservationFragment newInstance() {
        return new AddObservationFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddObservationBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(AddObservationViewModel.class);
        db = RoomHelper.initDatabase(getContext());
        mViewModel.setDatabase(db);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }

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
        // Camera
        binding.buttonTakePhoto.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // Save Observation Button
        binding.buttonSave.setOnClickListener(v -> this.handleSave());



        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // Save imageBitmap to a file and get the file path
                String photoPath = saveImage(imageBitmap);

                // Set photoPath to ObservationEntity
                ob.setPhotoPath(photoPath);

                // Display the image
                binding.imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    private String saveImage(Bitmap imageBitmap) {
        File filesDir = getContext().getFilesDir();
        String imageFileName = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";
        File imageFile = new File(filesDir, imageFileName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile.getAbsolutePath();
    }


    private void handleSave() {
        if (binding.editTextObservation.length() == 0) {
            showError(binding.editTextObservation, "Observation is Required");
        }
        if (binding.editTextDate.length() == 0) {
            showError(binding.editTextDate, "Date is Required");
        }

        if (binding.editTextObservation.length() > 0 &&
                binding.editTextDate.length() > 0
               ) {
            String observation = binding.editTextObservation.getText().toString();
            String date = binding.editTextDate.getText().toString();
            String comment = binding.editTextComment.getText().toString();


            int hikeId = getArguments().getInt("hikeId");
            HikeEntity hike = db.hikeDao().getHikeById(hikeId);

            ob.setObservation(observation);
            ob.setTimeOfObservation(date);
            ob.setAdditionalComments(comment);
            ob.setHikeEntity(hike);

            showConfirmationDialog(ob);



        }

    }



    private void showConfirmationDialog(ObservationEntity ob) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        builder.setIcon(R.drawable.baseline_check_circle_outline_24);
        builder.setMessage("Are these details correct? \n" +
                "Observation: " + ob.getObservation() + "\n" +
                "Location: " + ob.getTimeOfObservation() + "\n" +
                "Date: " + ob.getAdditionalComments() + "\n" +
                "Path Image: " + ob.getPhotoPath()

        );
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                mViewModel.insertObservation(ob);
                Navigation.findNavController(getView()).navigateUp();
                Toast.makeText(getContext(), "Add Observation Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Make Changes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}