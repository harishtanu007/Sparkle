package com.mindbriks.sparkle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.IDataSource;
import com.mindbriks.sparkle.interfaces.IDataSourceCallback;
import com.mindbriks.sparkle.interfaces.IUserDetailsCallback;
import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.utils.StringResourceHelper;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText userEmail, userPassword;
    private String userEmailText, userPasswordText;
    private ScrollView rootLayout;
    private Context context;
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rootLayout = findViewById(R.id.root_layout);
        userEmail = findViewById(R.id.email_field);
        userPassword = findViewById(R.id.password_field);
        context = getApplication();

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmailText = userEmail.getText().toString().trim();
                userPasswordText = userPassword.getText().toString().trim();
                if (userEmailText.length() <= 0) {
                    Snackbar.make(rootLayout, StringResourceHelper.getString(context, R.string.enter_valid_email), Snackbar.LENGTH_LONG).show();
                } else if (userPasswordText.length() <= 0) {
                    Snackbar.make(rootLayout, StringResourceHelper.getString(context, R.string.enter_password), Snackbar.LENGTH_LONG).show();
                } else {
                    mRegProgress = new ProgressDialog(LoginActivity.this, R.style.AppThemeDialog);
                    mRegProgress.setIndeterminate(true);
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.setMessage("Logging in...");
                    mRegProgress.show();
                    IDataSource dataSource = DataSourceHelper.getDataSource();
                    dataSource.login(userEmailText, userPasswordText, new IDataSourceCallback() {
                        @Override
                        public void onSuccess() {
                            mRegProgress.dismiss();
                            dataSource.getCurrentUserDetails(new IUserDetailsCallback() {
                                @Override
                                public void onUserDetailsFetched(DbUser userDetails) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onUserDetailsFetchFailed(String errorMessage) {
                                    Intent intent = new Intent(getApplicationContext(), SignUpDetailsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            mRegProgress.dismiss();
                            Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}