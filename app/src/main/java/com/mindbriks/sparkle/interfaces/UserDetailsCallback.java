package com.mindbriks.sparkle.interfaces;

import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.EncryptedDbUser;

public interface UserDetailsCallback {
    void onUserDetailsFetched(DbUser userDetails);
    void onUserDetailsFetchFailed(String errorMessage);
}
