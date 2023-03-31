package com.mindbriks.sparkle.ui.likes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.adapter.LikesAdapter;
import com.mindbriks.sparkle.databinding.FragmentLikesBinding;
import com.mindbriks.sparkle.model.Profile;
import com.mindbriks.sparkle.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class LikesFragment extends Fragment {

    private FragmentLikesBinding binding;
    private RecyclerView.Adapter mLikesAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LikesViewModel likesViewModel =
                new ViewModelProvider(this).get(LikesViewModel.class);

        binding = FragmentLikesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        populateUsers();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        RecyclerView mRecyclerView = binding.likes;
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setClipToPadding(false);
        mRecyclerView.setHasFixedSize(true);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.horizontal_card);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));

        mRecyclerView.setAdapter(mLikesAdapter);
        return root;
    }

    private void populateUsers() {
        mLikesAdapter = new LikesAdapter(getLikedUsers(), getContext());
    }

    private List<Profile> getLikedUsers() {
        List<Profile> profileList = new ArrayList<>();

        Profile profile1 = new Profile("1", "Harish", 21, "", 12);
        Profile profile2 = new Profile("2", "Barre", 26, "", 13);
        Profile profile3 = new Profile("3", "comp", 22, "", 14);


        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);
        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);
        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);
        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);
        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);
        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);
        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);


        return profileList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}