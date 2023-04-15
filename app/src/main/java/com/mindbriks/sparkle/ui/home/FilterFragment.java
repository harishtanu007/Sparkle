package com.mindbriks.sparkle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mindbriks.sparkle.R;

public class FilterFragment extends BottomSheetDialogFragment {
    private SeekBar distanceSeekBar;
    private TextView distanceTextView;
    private SeekBar ageRangeSeekBar;
    private TextView ageRangeTextView;
    private RadioGroup genderRadioGroup;
    private RadioButton genderRadioButton;

    private int distance;
    private int minAge;
    private int maxAge;
    private int gender;

    private OnFilterSelectedListener mListener;

    public void setOnFilterSelectedListener(OnFilterSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        distanceSeekBar = view.findViewById(R.id.seek_bar_distance);
        distanceTextView = view.findViewById(R.id.distance_text_view);
        ageRangeSeekBar = view.findViewById(R.id.age_range_seek_bar);
        ageRangeTextView = view.findViewById(R.id.age_range_text_view);
        genderRadioGroup = view.findViewById(R.id.radio_group_gender);

        distance = distanceSeekBar.getProgress();
        minAge = ageRangeSeekBar.getProgress();
        maxAge = ageRangeSeekBar.getMax();
        gender = genderRadioGroup.getCheckedRadioButtonId();

        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance = progress;
                distanceTextView.setText(getResources().getString(R.string.distance_format, distance));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ageRangeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageRangeTextView.setText(getResources().getString(R.string.age_range));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                genderRadioButton = view.findViewById(checkedId);
                gender = (int) genderRadioButton.getTag();
            }
        });

        view.findViewById(R.id.button_apply_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onFilterSelected(distance, minAge, maxAge, gender);
                }
                dismiss();
            }
        });

        return view;
    }

    public interface OnFilterSelectedListener {
        void onFilterSelected(int distance, int minAge, int maxAge, int gender);
    }
}
