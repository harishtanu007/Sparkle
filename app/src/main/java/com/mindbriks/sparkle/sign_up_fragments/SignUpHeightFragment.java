package com.mindbriks.sparkle.sign_up_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.fragment.app.Fragment;

import com.mindbriks.sparkle.R;

public class SignUpHeightFragment extends Fragment {

    private NumberPicker heightPicker;

    private String[] heights;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.signup_height_fragment, container, false);
        heights = new String[]{"4'0\"", "4'1\"", "4'2\"", "4'3\"", "4'4\"", "4'5\"", "4'6\"", "4'7\"", "4'8\"", "4'9\"", "4'10\"", "4'11\"",
                "5'0\"", "5'1\"", "5'2\"", "5'3\"", "5'4\"", "5'5\"", "5'6\"", "5'7\"", "5'8\"", "5'9\"", "5'10\"", "5'11\"",
                "6'0\"", "6'1\"", "6'2\"", "6'3\"", "6'4\"", "6'5\"", "6'6\"", "6'7\"", "6'8\"", "6'9\"", "6'10\"", "6'11\"",
                "7'0\"", "7'1\"", "7'2\"", "7'3\"", "7'4\"", "7'5\"", "7'6\"", "7'7\"", "7'8\"", "7'9\"", "7'10\"", "7'11\""};
        heightPicker = view.findViewById(R.id.height_picker);
        heightPicker.setMinValue(0);
        heightPicker.setMaxValue(heights.length - 1);
        heightPicker.setDisplayedValues(heights);
        return view;
    }

    public String getUserHeight() {
        return heights[heightPicker.getValue()];
    }
}
