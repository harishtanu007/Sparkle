package com.mindbriks.sparkle.interfaces;

import com.mindbriks.sparkle.model.DbUser;

import java.util.List;

public interface AllUserDetailsCallback {
    void onUserDetailsFetched(List<DbUser> userDetails);
    void onUserDetailsFetchFailed(String errorMessage);
}
