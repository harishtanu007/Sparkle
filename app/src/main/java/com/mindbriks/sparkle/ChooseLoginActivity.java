package com.mindbriks.sparkle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.DataSource;
import com.mindbriks.sparkle.interfaces.LoginVerificationListener;

public class ChooseLoginActivity extends AppCompatActivity {

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
                verifyUserLoggedIn();
                return false;
            }
        });

        setContentView(R.layout.activity_choose_login);
        Button mSignInGoogleButton = findViewById(R.id.login_google_button);
        mSignInGoogleButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignUpDetailsActivity.class);
            startActivity(intent);
        });

        Button mSignInEmailButton = findViewById(R.id.login_email_button);
        mSignInEmailButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        TextView signUpText = findViewById(R.id.sign_up_text);
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });
    }

    //TODO: Update this method to check if the user has already logged in
    private void verifyUserLoggedIn() {
        DataSource dataSource = DataSourceHelper.getDataSource();
        dataSource.onLoginVerification(new LoginVerificationListener() {
            @Override
            public void onLoginVerification(boolean isLoggedIn) {
                if(isLoggedIn){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}