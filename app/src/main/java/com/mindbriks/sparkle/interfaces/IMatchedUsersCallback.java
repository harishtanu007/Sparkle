package com.mindbriks.sparkle.interfaces;

import java.util.List;

public interface IMatchedUsersCallback {
    void onMatchedUsersFound(List<String> matchedUsers);

    void onMatchedUsersFailed(String errorMessage);
}
