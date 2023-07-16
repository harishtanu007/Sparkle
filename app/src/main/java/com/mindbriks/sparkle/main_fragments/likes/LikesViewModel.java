package com.mindbriks.sparkle.main_fragments.likes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IAllUserDetailsCallback;
import com.mindbriks.sparkle.interfaces.IDataSource;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.model.DbUser;

import java.util.List;

public class LikesViewModel extends ViewModel {

    private IDataSource dataSource;
    private MutableLiveData<List<DbUser>> likedUsers;

    public LikesViewModel() {
        likedUsers = new MutableLiveData<>();
    }

    public void loadMyData() {
        dataSource = DataSourceHelper.getDataSource();
        String currentUserId = dataSource.getCurrentUserId();

        dataSource.getCurrentUserDetails(new IUserDetailsCallback() {
            @Override
            public void onUserDetailsFetched(DbUser userDetails) {
                dataSource.getLikedUserDetails(currentUserId, new IAllUserDetailsCallback() {
                    @Override
                    public void onUserDetailsFetched(List<DbUser> users) {
                        //List<DbUser> matchedUsers = MatchingAlgorithm.getTopMatches(userDetails, users);
                        likedUsers.setValue(users);
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