package com.mindbriks.sparkle.main_fragments.likes;

import android.view.animation.LinearInterpolator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IAllUserDetailsCallback;
import com.mindbriks.sparkle.interfaces.IDataSource;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.model.DbUser;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.List;

public class LikesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private IDataSource dataSource;
    private MutableLiveData<List<DbUser>> likedUsers;

    public LikesViewModel() {
        mText = new MutableLiveData<>();
        likedUsers = new MutableLiveData<>();
        mText.setValue("This is home fragment");

    }
    public LiveData<String> getText() {
        return mText;
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
                        LikesViewModel.this.likedUsers.setValue(users);
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
        return likedUsers;
    }
}