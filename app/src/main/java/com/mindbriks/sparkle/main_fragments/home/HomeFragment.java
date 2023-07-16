package com.mindbriks.sparkle.main_fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mindbriks.sparkle.adapter.ProfileAdapter;
import com.mindbriks.sparkle.databinding.ButtonContainerBinding;
import com.mindbriks.sparkle.databinding.FragmentHomeBinding;
import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IDataSourceCallback;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
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
    private DbUser mCurrentUser;
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
        DataSourceHelper.getDataSource().getCurrentUserDetails(new IUserDetailsCallback() {
            @Override
            public void onUserDetailsFetched(DbUser userDetails) {
                if (userDetails == null) {
                    Snackbar.make(rootLayout, "Error while retrieving user details", Snackbar.LENGTH_LONG).show();
                }
                setupButton();
                populateUsers(homeViewModel, userDetails);
                filterButton.setOnClickListener(v -> {
                    FilterFragment filterFragment = new FilterFragment();
                    filterFragment.show(getFragmentManager(), "FilterFragment");
                });
            }

            @Override
            public void onUserDetailsFetchFailed(String errorMessage) {

            }
        });
        return root;
    }

    private void populateUsers(HomeViewModel homeViewModel, DbUser dbUser) {
        profileAdapter = new ProfileAdapter(getContext(), dbUser);
        cardStackView.setAdapter(profileAdapter);
        homeViewModel.getMatchedUsers().observe(getViewLifecycleOwner(), new Observer<List<DbUser>>() {
            @Override
            public void onChanged(List<DbUser> myDataList) {
                profileAdapter.setMyDataList(myDataList);
            }
        });
    }

    // set up buttons actions
    private void setupButton() {
        // skip button
        FloatingActionButton skip = binding.buttonContainer.skipButton.skipButton;
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
//            AlertDialog.Builder builderSingle = new MaterialAlertDialogBuilder(getContext(), R.style.MyRoundedMaterialDialog);
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            View customView = inflater.inflate(R.layout.match_layout, null);
//            builderSingle.setView(customView);
//            builderSingle.setCancelable(false);
//            AlertDialog dialog = builderSingle.create();
//            dialog.show();
//            Button sendMessage = customView.findViewById(R.id.match_send_message);
//            sendMessage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
            homeViewModel.setUserLiked(mCurrentUser);
        }
        else {
            homeViewModel.setUserDisLiked(mCurrentUser, new IDataSourceCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(String errorMessage) {
                    Snackbar.make(rootLayout, "Error while sending like to this user", Snackbar.LENGTH_LONG).show();
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
        List<DbUser> users = homeViewModel.getMatchedUsers().getValue();
        if (users == null || users.isEmpty()) {
            binding.buttonContainer.buttonContainer.setVisibility(View.GONE);
        } else {
            binding.buttonContainer.buttonContainer.setVisibility(View.VISIBLE);
            mCurrentUser = users.get(position);
        }
    }

    @Override
    public void onCardDisappeared(View view, int position) {
        List<DbUser> users = homeViewModel.getMatchedUsers().getValue();
        if (users == null || users.size() <= 1)
            binding.buttonContainer.buttonContainer.setVisibility(View.GONE);
        else
            binding.buttonContainer.buttonContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFilterSelected(int distance, int minAge, int maxAge, int gender) {

    }

    private void updateUi(int position){
        List<DbUser> users = homeViewModel.getMatchedUsers().getValue();
        if(users.get(position) != null)
            users.remove(position);
        if (users.isEmpty())
            binding.buttonContainer.buttonContainer.setVisibility(View.GONE);
        else
            binding.buttonContainer.buttonContainer.setVisibility(View.VISIBLE);
    }
}