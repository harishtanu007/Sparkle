package com.mindbriks.sparkle.main_fragments.home;

import android.view.animation.LinearInterpolator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IAllUserDetailsCallback;
import com.mindbriks.sparkle.interfaces.IDataSource;
import com.mindbriks.sparkle.interfaces.IDataSourceCallback;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.model.DbUser;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private IDataSource dataSource;
    private MutableLiveData<List<DbUser>> matchedUsers;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        matchedUsers = new MutableLiveData<>();
        mText.setValue("This is home fragment");

    }
    public LiveData<String> getText() {
        return mText;
    }

    // set Up cardStack animation
    public void setUpCardStack(CardStackLayoutManager layoutManager, CardStackView cardStackView) {
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setStackFrom(StackFrom.None);
        layoutManager.setVisibleCount(3);
        layoutManager.setTranslationInterval(8.0f);
        layoutManager.setScaleInterval(0.95f);
        layoutManager.setSwipeThreshold(0.3f);
        layoutManager.setMaxDegree(20.0f);
        layoutManager.setDirections(Direction.HORIZONTAL);
        layoutManager.setCanScrollHorizontal(true);
        layoutManager.setCanScrollVertical(true);
        layoutManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual);
        layoutManager.setOverlayInterpolator(new LinearInterpolator());
        cardStackView.setLayoutManager(layoutManager);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    public void loadMyData() {
        dataSource = DataSourceHelper.getDataSource();
        String currentUserId = dataSource.getCurrentUserId();

        dataSource.getCurrentUserDetails(new IUserDetailsCallback() {
            @Override
            public void onUserDetailsFetched(DbUser userDetails) {
                dataSource.getAllUserDetails(currentUserId, new IAllUserDetailsCallback() {
                    @Override
                    public void onUserDetailsFetched(List<DbUser> users) {
                        //List<DbUser> matchedUsers = MatchingAlgorithm.getTopMatches(userDetails, users);
                        HomeViewModel.this.matchedUsers.setValue(users);
                    }

                    @Override
                    public void onUserDetailsFetchFailed(String errorMessage) {

                    }
                });
            }

            @Override
            public void onUserDetailsFetchFailed(String errorMessage) {

            }
        });
    }

    public LiveData<List<DbUser>> getMatchedUsers() {
        return matchedUsers;
    }

    public void setUserLiked(DbUser likedUser) {
        dataSource = DataSourceHelper.getDataSource();
        String currentUserId = dataSource.getCurrentUserId();
        dataSource.setUserLiked(currentUserId, likedUser.getId(), new IDataSourceCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    public void setUserDisLiked(DbUser dislikedUser, IDataSourceCallback callback) {
        dataSource = DataSourceHelper.getDataSource();
        String currentUserId = dataSource.getCurrentUserId();
        dataSource.setUserDisliked(currentUserId, dislikedUser.getId(), new IDataSourceCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }
}