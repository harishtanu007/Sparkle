package com.mindbriks.sparkle.sign_up_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.adapter.PreferencesAdapter;
import com.mindbriks.sparkle.databinding.SignupGenderFragmentBinding;
import com.mindbriks.sparkle.model.GenderPreference;
import com.mindbriks.sparkle.model.SmokingPreference;

import java.util.Arrays;
import java.util.List;

public class SignUpGenderFragment extends Fragment {

    private SignupGenderFragmentBinding binding;
    private PreferencesAdapter mGenderAdapter;

    private String gender;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SignupGenderFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        populateGenderPreferences();

        RecyclerView mGenderList = binding.genderList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mGenderList.setLayoutManager(layoutManager);
        mGenderList.setClipToPadding(false);
        mGenderList.setHasFixedSize(true);

        mGenderList.setAdapter(mGenderAdapter);
        return root;
    }

    private void populateGenderPreferences() {
        mGenderAdapter = new PreferencesAdapter(getGenderPreferences(), getContext());
    }

    private List<GenderPreference> getGenderPreferences() {
        List<GenderPreference> genderPreferences = Arrays.asList(GenderPreference.values());
        return genderPreferences;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String getGenderPreference() {
        SmokingPreference smokingPreference = (SmokingPreference) mGenderAdapter.getSelectedItem();
        if (smokingPreference != null)
            return smokingPreference.name();
        return "";
    }
}
