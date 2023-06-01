package com.mindbriks.sparkle.interfaces;

import com.mindbriks.sparkle.model.DbUser;

public interface IUserDetailsCallback {
    void onUserDetailsFetched(DbUser userDetails);
    void onUserDetailsFetchFailed(String errorMessage);
}
