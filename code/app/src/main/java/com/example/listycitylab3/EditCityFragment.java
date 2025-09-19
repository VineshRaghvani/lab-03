package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    private static final String ARG_CITY_NAME = "city_name";
    private static final String ARG_PROVINCE_NAME = "province_name";
    private static final String ARG_POSITION = "position";

    interface EditCityDialogListener {
        void editCity(City city, int position);
    }

    private EditCityDialogListener listener;
    private EditText editCityName;
    private EditText editProvinceName;

    private String originalCityName;
    private String originalProvinceName;
    private int position;

    // Factory method to create a new instance and pass arguments
    public static EditCityFragment newInstance(City city, int position) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_NAME, city.getName());
        args.putString(ARG_PROVINCE_NAME, city.getProvince());
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement EditCityDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            originalCityName = getArguments().getString(ARG_CITY_NAME);
            originalProvinceName = getArguments().getString(ARG_PROVINCE_NAME);
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the same layout as AddCityFragment or create a new one
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        editCityName = view.findViewById(R.id.edit_text_city_text);
        editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Pre-fill the EditText fields
        if (originalCityName != null) {
            editCityName.setText(originalCityName);
        }
        if (originalProvinceName != null) {
            editProvinceName.setText(originalProvinceName);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit City") // Change title
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (!cityName.isEmpty() && !provinceName.isEmpty()) { // Basic validation
                        listener.editCity(new City(cityName, provinceName), position);
                    }
                })
                .create();
    }
}
