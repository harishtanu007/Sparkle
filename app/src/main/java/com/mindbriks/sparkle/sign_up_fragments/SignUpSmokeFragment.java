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
import com.mindbriks.sparkle.databinding.SignupSmokeFragmentBinding;
import com.mindbriks.sparkle.model.SmokingPreference;

import java.util.Arrays;
import java.util.List;

public class SignUpSmokeFragment extends Fragment {

    private SignupSmokeFragmentBinding binding;
    private PreferencesAdapter mSmokeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SignupSmokeFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        populateSmokePreferences();

        RecyclerView mGenderList = binding.smokeList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mGenderList.setLayoutManager(layoutManager);
        mGenderList.setClipToPadding(false);
        mGenderList.setHasFixedSize(true);

        mGenderList.setAdapter(mSmokeAdapter);
        return root;
    }

    private void populateSmokePreferences() {
        mSmokeAdapter = new PreferencesAdapter(getSmokingPreferences(), getContext());
    }

    private List<SmokingPreference> getSmokingPreferences() {
        List<SmokingPreference> smokingPreferences = Arrays.asList(SmokingPreference.values());
        return smokingPreferences;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public SmokingPreference getSmokingPreference() {
        return  (SmokingPreference) mSmokeAdapter.getSelectedItem();
    }
}
