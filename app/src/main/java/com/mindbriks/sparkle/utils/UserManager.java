package com.mindbriks.sparkle.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mindbriks.sparkle.cache.UserCache;
import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IDataSource;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.interfaces.IUserManager;
import com.mindbriks.sparkle.model.DbUser;

public class UserManager implements IUserManager{
    private static IUserManager userManager;
    IDataSource dataSource;

    private UserManager() {
        // Private constructor to enforce singleton pattern
        dataSource = DataSourceHelper.getDataSource();
    }

    public static IUserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    @Override
    public void getCurrentUserDetails(Context context, IUserDetailsCallback callback) {
        dataSource = DataSourceHelper.getDataSource();
        DbUser user = UserCache.getInstance().getCachedUser(); // Get user from cache

        if (user == null) {
            // Check for internet connection
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // Internet connection is available
                // Fetch user details from Firebase
                dataSource.getCurrentUserDetails(new IUserDetailsCallback() {

                    @Override
                    public void onUserDetailsFetched(DbUser userDetails) {
                        callback.onUserDetailsFetched(userDetails);
                    }

                    @Override
                    public void onUserDetailsFetchFailed(String errorMessage) {
                        callback.onUserDetailsFetchFailed(errorMessage);
                    }
                });
            }
            else{
                callback.onUserDetailsFetchFailed("No internet");
            }
        }
    }
}
