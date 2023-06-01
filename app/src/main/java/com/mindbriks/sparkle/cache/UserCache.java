package com.mindbriks.sparkle.cache;

import com.mindbriks.sparkle.model.DbUser;

public class UserCache {
    private static UserCache instance;
    private DbUser currentUser;

    private UserCache() {
        // Private constructor to enforce singleton pattern
    }

    public static UserCache getInstance() {
        if (instance == null) {
            instance = new UserCache();
        }
        return instance;
    }

    public void cacheUser(DbUser user) {
        currentUser = user;
    }

    public DbUser getCachedUser() {
        return currentUser;
    }

    public boolean isUserCached() {
        return currentUser != null;
    }
}



