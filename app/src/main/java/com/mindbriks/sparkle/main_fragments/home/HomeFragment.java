package com.mindbriks.sparkle.main_fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mindbriks.sparkle.R;
import com.mindbriks.sparkle.adapter.ProfileAdapter;
import com.mindbriks.sparkle.databinding.FragmentHomeBinding;
import com.mindbriks.sparkle.model.DbUser;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.List;


public class HomeFragment extends Fragment implements CardStackListener, FilterFragment.OnFilterSelectedListener {

    HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private CardStackLayoutManager layoutManager;
    private CardStackView cardStackView;
    private ProfileAdapter profileAdapter;
    private ImageView filterButton;
    private DbUser mUser;
    private LinearLayout rootLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.loadMyData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rootLayout = binding.rootLayout;


        cardStackView = binding.cardStackView;
        filterButton = binding.filter;
        layoutManager = new CardStackLayoutManager(getContext(), this);
        homeViewModel.setUpCardStack(layoutManager, cardStackView);
        setupButton(binding);
        populateUsers();
        filterButton.setOnClickListener(v -> {
            FilterFragment filterFragment = new FilterFragment();
            filterFragment.show(getFragmentManager(), "FilterFragment");
        });
        return root;
    }

    private void populateUsers() {
        profileAdapter = new ProfileAdapter(getContext());
        cardStackView.setAdapter(profileAdapter);
        homeViewModel.getMatchedUsers().observe(getViewLifecycleOwner(), new Observer<List<DbUser>>() {
            @Override
            public void onChanged(List<DbUser> myDataList) {
                profileAdapter.setMyDataList(myDataList);
            }
        });
    }

    // set up buttons actions
    private void setupButton(FragmentHomeBinding homeView) {
        // skip button
        FloatingActionButton skip = homeView.buttonContainer.skipButton.skipButton;
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
        FloatingActionButton like = binding.buttonContainer.likeButton.likeButton;
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
        if (direction.equals(Direction.Right)) {
            AlertDialog.Builder builderSingle = new MaterialAlertDialogBuilder(getContext(), R.style.MyRoundedMaterialDialog);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View customView = inflater.inflate(R.layout.match_layout, null);
            builderSingle.setView(customView);
            builderSingle.setCancelable(false);
            AlertDialog dialog = builderSingle.create();
            dialog.show();
            Button sendMessage = customView.findViewById(R.id.match_send_message);
            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
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

    @Override
    public void onFilterSelected(int distance, int minAge, int maxAge, int gender) {

    }
}