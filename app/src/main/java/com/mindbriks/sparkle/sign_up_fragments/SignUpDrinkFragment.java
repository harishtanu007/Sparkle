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
import com.mindbriks.sparkle.databinding.SignupDrinkFragmentBinding;
import com.mindbriks.sparkle.model.DrinkingPreference;

import java.util.Arrays;
import java.util.List;

public class SignUpDrinkFragment extends Fragment {

    private SignupDrinkFragmentBinding binding;
    private PreferencesAdapter mDrinkAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SignupDrinkFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        populateDrinkPreferences();

        RecyclerView mDrinkingPreferences = binding.drinkList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mDrinkingPreferences.setLayoutManager(layoutManager);
        mDrinkingPreferences.setClipToPadding(false);
        mDrinkingPreferences.setHasFixedSize(true);

        mDrinkingPreferences.setAdapter(mDrinkAdapter);
        return root;
    }

    private void populateDrinkPreferences() {
        mDrinkAdapter = new PreferencesAdapter(getDrinkingPreferences(), getContext());
    }

    private List<DrinkingPreference> getDrinkingPreferences() {
        List<DrinkingPreference> drinkingPreferences = Arrays.asList(DrinkingPreference.values());
        return drinkingPreferences;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String getDrinkingPreference() {
        DrinkingPreference drinkingPreference = (DrinkingPreference) mDrinkAdapter.getSelectedItem();
        if (drinkingPreference != null)
            return drinkingPreference.name();
        return "";
    }
}
