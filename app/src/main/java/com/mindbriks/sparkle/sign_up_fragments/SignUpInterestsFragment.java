package com.mindbriks.sparkle.sign_up_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindbriks.sparkle.adapter.InterestsAdapter;
import com.mindbriks.sparkle.databinding.SignupInterestsFragmentBinding;
import com.mindbriks.sparkle.model.Interest;

import java.util.ArrayList;
import java.util.List;

public class SignUpInterestsFragment extends Fragment {

    private SignupInterestsFragmentBinding binding;
    private InterestsAdapter mInterestsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SignupInterestsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        populateInterests();

        RecyclerView mInterestsList = binding.interestsList;

        // Set the GridLayoutManager with dynamic span count

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mInterestsList.setLayoutManager(layoutManager);

        mInterestsList.setAdapter(mInterestsAdapter);
        return root;
    }

    private void populateInterests() {
        mInterestsAdapter = new InterestsAdapter(getInterests(), getContext());
    }

    private List<Interest> getInterests() {
        List<Interest> interestList = new ArrayList<>();

        interestList.add(new Interest("Sports"));
        interestList.add(new Interest("Movies"));
        interestList.add(new Interest("Music"));
        interestList.add(new Interest("Hiking"));
        interestList.add(new Interest("Fishing"));
        interestList.add(new Interest("Singing"));
        interestList.add(new Interest("Biking"));
        interestList.add(new Interest("Sports"));
        interestList.add(new Interest("Gym"));
        interestList.add(new Interest("Instagram"));
        interestList.add(new Interest("Travel"));
        interestList.add(new Interest("Walking"));
        interestList.add(new Interest("Meditation"));
        interestList.add(new Interest("Partying"));
        interestList.add(new Interest("Heavy Metal"));
        interestList.add(new Interest("Painting"));
        interestList.add(new Interest("Reading"));

        return interestList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public List<Interest> getSelectedInterests(){
        return mInterestsAdapter.getSelectedInterests();
    }

}