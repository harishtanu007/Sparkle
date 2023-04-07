package com.mindbriks.sparkle.sign_up_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.mindbriks.sparkle.R;

public class SignUpGenderFragment extends Fragment {

    private EditText fullNameEditText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.signup_gender_fragment, container, false);
        //fullNameEditText = view.findViewById(R.id.sign_up_full_name);
        return view;
    }

    public String getFullName() {
        return fullNameEditText.getText().toString().trim();
    }
}
