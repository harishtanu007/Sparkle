package com.mindbriks.sparkle.firebase;

import com.mindbriks.sparkle.interfaces.DataSource;

public class DataSourceHelper {
    public static final boolean userEncryption = true;

    public static boolean shouldEncryptUser() {
        return userEncryption;
    }

    public static DataSource getDataSource() {
        return new FirebaseDataSource();
    }
}
