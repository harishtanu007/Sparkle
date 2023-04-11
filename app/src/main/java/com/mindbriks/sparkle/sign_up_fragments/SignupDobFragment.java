package com.mindbriks.sparkle.sign_up_fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.mindbriks.sparkle.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class SignupDobFragment extends Fragment {

    private EditText dobText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.signup_dob_fragment, container, false);
        dobText = view.findViewById(R.id.sign_up_dob);
        dobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dobText.getText().toString().isEmpty())
            dobText.post(new Runnable() {
                @Override
                public void run() {
                    showDatePickerDialog();
                }
            });
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DecimalFormat mFormat = new DecimalFormat("00");
                        mFormat.setRoundingMode(RoundingMode.DOWN);
                        String date = mFormat.format(Double.valueOf(dayOfMonth)) + "/" + mFormat.format(Double.valueOf(month)) + "/" + year;
                        dobText.setText(date);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        //User can only register if they are 18 years old
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

}
