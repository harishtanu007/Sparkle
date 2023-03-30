package com.mindbriks.sparkle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.core.splashscreen.SplashScreen;

public class LoginActivity extends AppCompatActivity {

    boolean isAndroidReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        View content = findViewById(android.R.id.content);

        content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isAndroidReady)
                    content.getViewTreeObserver().removeOnPreDrawListener(this);
                dimissSplashScreen();
                return false;
            }
        });

        setContentView(R.layout.activity_main);
    }

    private void dimissSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isAndroidReady = true;
            }
        }, 1000);
    }
}