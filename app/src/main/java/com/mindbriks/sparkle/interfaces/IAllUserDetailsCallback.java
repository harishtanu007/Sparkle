package com.mindbriks.sparkle.interfaces;

import com.mindbriks.sparkle.model.DbUser;

import java.util.List;

public interface IAllUserDetailsCallback {
    void onUserDetailsFetched(List<DbUser> userDetails);
    void onUserDetailsFetchFailed(String errorMessage);
}
