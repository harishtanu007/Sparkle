package com.mindbriks.sparkle.sign_up_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.adapter.InterestAdapter;
import com.mindbriks.sparkle.model.InterestCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpInterestsFragment extends Fragment {
    private ExpandableListView expandableListView;
    private List<InterestCategory> interestCategories;
    private InterestAdapter interestAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_interests_fragment, container, false);
        // Set up the ExpandableListView
        expandableListView = view.findViewById(R.id.expandable_list_view);
        interestCategories = new ArrayList<>();
        interestCategories.add(new InterestCategory("Sports", Arrays.asList("Cricket", "Badminton", "Football")));
        interestCategories.add(new InterestCategory("Music", Arrays.asList("Rock", "Pop", "Hip-hop")));
        interestCategories.add(new InterestCategory("Movies", Arrays.asList("Action", "Comedy", "Drama")));
        interestAdapter = new InterestAdapter(getContext(), interestCategories);
        expandableListView.setAdapter(interestAdapter);
        return view;
    }
}
