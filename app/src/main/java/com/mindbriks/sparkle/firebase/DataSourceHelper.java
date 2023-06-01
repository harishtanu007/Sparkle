package com.mindbriks.sparkle.firebase;

import com.mindbriks.sparkle.interfaces.IDataSource;

public class DataSourceHelper {
    public static final boolean userEncryption = false;

    private static IDataSource instance;

    private DataSourceHelper() {
        // Private constructor to enforce singleton pattern
    }

    public static IDataSource getDataSource() {
        if (instance == null) {
            instance = new FirebaseDataSource();
        }
        return instance;
    }

    public static boolean shouldEncryptUser() {
        return userEncryption;
    }
}
