package com.mindbriks.sparkle;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class LoginActivity extends AppCompatActivity {

    boolean isAndroidReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
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

        setContentView(R.layout.activity_login);
        Button mSignInButton = findViewById(R.id.sign_in_button);
        //sign button
        mSignInButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    //TODO: Update this method to check if the user has already logged in
    private void dimissSplashScreen() {
        new Handler().postDelayed(() -> isAndroidReady = true, 1000);
    }
}