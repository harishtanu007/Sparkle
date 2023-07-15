package com.mindbriks.sparkle;

import com.google.firebase.database.FirebaseDatabase;

public class Sparkle extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}