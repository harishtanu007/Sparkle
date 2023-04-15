package com.mindbriks.sparkle;

import static com.mindbriks.sparkle.utils.StringResourceHelper.checkCapitalLetters;
import static com.mindbriks.sparkle.utils.StringResourceHelper.isValidEmail;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mindbriks.sparkle.databinding.ActivitySignUpBinding;
import com.mindbriks.sparkle.firebase.FirebaseDataSource;
import com.mindbriks.sparkle.interfaces.DataSource;
import com.mindbriks.sparkle.interfaces.DataSourceCallback;
import com.mindbriks.sparkle.model.User;
import com.mindbriks.sparkle.utils.StringResourceHelper;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText userEmail, userPassword, checkPassword;
    private String userEmailText, userPasswordText, checkPasswordText;
    private ScrollView rootLayout;
    private Context context;
    private ActivitySignUpBinding binding;
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userEmail = binding.emailField;
        userPassword = binding.passwordField;
        checkPassword = binding.confirmPasswordField;
        rootLayout = binding.rootLayout;
        context = getApplication();

        Button signUpButton = binding.signUpButton;
        signUpButton.setOnClickListener(v -> {
            userEmailText = userEmail.getText().toString().trim();
            userPasswordText = userPassword.getText().toString().trim();
            checkPasswordText = checkPassword.getText().toString().trim();
            if (checkCapitalLetters(userEmailText)) {
                Snackbar.make(rootLayout, StringResourceHelper.getString(context, R.string.email_cannot_contain_capital_letters), Snackbar.LENGTH_LONG).show();
            } else if (!isValidEmail(userEmailText)) {
                Snackbar.make(rootLayout, StringResourceHelper.getString(context, R.string.enter_valid_email), Snackbar.LENGTH_LONG).show();
            } else if (userPasswordText.length() <= 0) {
                Snackbar.make(rootLayout, StringResourceHelper.getString(context, R.string.enter_password), Snackbar.LENGTH_LONG).show();
            } else if (!checkPasswordText.equals(userPasswordText)) {
                Snackbar.make(rootLayout, StringResourceHelper.getString(context, R.string.passwords_not_match), Snackbar.LENGTH_LONG).show();
            } else {
                mRegProgress = new ProgressDialog(SignUpActivity.this, R.style.AppThemeDialog);
                mRegProgress.setIndeterminate(true);
                mRegProgress.setCanceledOnTouchOutside(false);
                mRegProgress.setMessage("Creating Account...");
                mRegProgress.show();
                User user = new User(userEmailText, userPasswordText);
                DataSource dataSource = new FirebaseDataSource();
                dataSource.createUser(user, new DataSourceCallback() {
                    @Override
                    public void onSuccess() {
                        mRegProgress.dismiss();
                        if (dataSource.isUserDetailsExist()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), SignUpDetailsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        mRegProgress.dismiss();
                        Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        TextView loginText = binding.loginText;
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}