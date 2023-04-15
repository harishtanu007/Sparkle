package com.mindbriks.sparkle.sign_up_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.adapter.GenderAdapter;
import com.mindbriks.sparkle.databinding.SignupGenderFragmentBinding;
import com.mindbriks.sparkle.model.Gender;

import java.util.ArrayList;
import java.util.List;

public class SignUpGenderFragment extends Fragment {

    private SignupGenderFragmentBinding binding;
    private RecyclerView.Adapter mGenderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SignupGenderFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        populateInterests();

        RecyclerView mGenderList = binding.genderList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mGenderList.setLayoutManager(layoutManager);
        mGenderList.setClipToPadding(false);
        mGenderList.setHasFixedSize(true);

        mGenderList.setAdapter(mGenderAdapter);
        return root;
    }

    private void populateInterests() {
        mGenderAdapter = new GenderAdapter(getInterests(), getContext());
    }

    private List<Gender> getInterests() {
        List<Gender> genderList = new ArrayList<>();

        Gender gender1 = new Gender("Man");
        Gender gender2 = new Gender("Woman");
        Gender gender3 = new Gender("Neither");

        genderList.add(gender1);
        genderList.add(gender2);
        genderList.add(gender3);

        return genderList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
