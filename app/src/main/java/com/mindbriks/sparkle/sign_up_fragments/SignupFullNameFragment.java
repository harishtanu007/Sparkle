package com.mindbriks.sparkle.sign_up_fragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mindbriks.sparkle.R;

public class SignupFullNameFragment extends Fragment {

    private EditText fullNameEditText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.signup_full_name_fragment, container, false);
        fullNameEditText = view.findViewById(R.id.sign_up_full_name);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        openKeyboard();
    }

    private void openKeyboard(){
        fullNameEditText.post(new Runnable() {
            @Override
            public void run() {
                fullNameEditText.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        fullNameEditText.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }

    public String getFullName() {
        return fullNameEditText.getText().toString().trim();
    }
}
