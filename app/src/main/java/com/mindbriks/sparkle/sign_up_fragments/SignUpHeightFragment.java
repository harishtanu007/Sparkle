package com.mindbriks.sparkle.sign_up_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.mindbriks.sparkle.R;

public class SignUpHeightFragment extends Fragment {

    private EditText heightEditText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.signup_height_fragment, container, false);
        heightEditText = view.findViewById(R.id.sign_up_height);
        return view;
    }

    public String getFullName() {
        return heightEditText.getText().toString().trim();
    }
}
