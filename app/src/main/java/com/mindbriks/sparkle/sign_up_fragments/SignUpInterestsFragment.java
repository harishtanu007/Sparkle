package com.mindbriks.sparkle.sign_up_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.adapter.InterestsAdapter;
import com.mindbriks.sparkle.databinding.SignupInterestsFragmentBinding;
import com.mindbriks.sparkle.model.Interest;

import java.util.ArrayList;
import java.util.List;

public class SignUpInterestsFragment extends Fragment {

    private SignupInterestsFragmentBinding binding;
    private RecyclerView.Adapter mInterestsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SignupInterestsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        populateInterests();

        RecyclerView mInterestsList = binding.interestsList;
        mInterestsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mInterestsList.setClipToPadding(false);
        mInterestsList.setHasFixedSize(true);

        mInterestsList.setAdapter(mInterestsAdapter);
        return root;
    }

    private void populateInterests() {
        mInterestsAdapter = new InterestsAdapter(getInterests(), getContext());
    }

    private List<Interest> getInterests() {
        List<Interest> interestList = new ArrayList<>();

        Interest interest1 = new Interest("Sports");
        Interest interest2 = new Interest("Movies");
        Interest interest3 = new Interest("Music");

        interestList.add(interest1);
        interestList.add(interest2);
        interestList.add(interest3);

        return interestList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}