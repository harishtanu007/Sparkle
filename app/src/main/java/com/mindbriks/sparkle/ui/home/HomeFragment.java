package com.mindbriks.sparkle.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mindbriks.sparkle.adapter.ProfileAdapter;
import com.mindbriks.sparkle.databinding.FragmentHomeBinding;
import com.mindbriks.sparkle.model.Profile;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CardStackListener {

    private FragmentHomeBinding binding;
    private CardStackLayoutManager layoutManager;
    private CardStackView cardStackView;
    private ProfileAdapter profileAdapter;
    List<Profile> profileList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        cardStackView = binding.cardStackView;
        layoutManager = new CardStackLayoutManager(getContext(), this);
        homeViewModel.setUpCardStack(layoutManager, cardStackView);
        setupButton(binding);
        populateUsers();
        return root;
    }

    private void populateUsers() {
        profileList = new ArrayList<>();

        Profile profile1 = new Profile("1", "Harish", 21, "", 12);
        Profile profile2 = new Profile("2", "Barre", 26, "", 13);
        Profile profile3 = new Profile("3", "comp", 22, "", 14);

        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);

        profileAdapter = new ProfileAdapter(getContext(), profileList);
        cardStackView.setAdapter(profileAdapter);
    }

    // set up buttons actions
    private void setupButton(FragmentHomeBinding homeView) {
        // skip button
        FloatingActionButton skip = homeView.skipButton.skipButton;
        skip.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            layoutManager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });
        // like button
        FloatingActionButton like = binding.likeButton.likeButton;
        like.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            layoutManager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {

    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}