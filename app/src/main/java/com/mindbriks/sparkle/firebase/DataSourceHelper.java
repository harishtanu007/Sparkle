package com.mindbriks.sparkle.firebase;

import com.mindbriks.sparkle.interfaces.DataSource;

public class DataSourceHelper {
    public static DataSource getDataSource(){
        return new FirebaseDataSource();
    }
}
