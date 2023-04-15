package com.mindbriks.sparkle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.mindbriks.sparkle.adapter.SignUpPagerAdapter;
import com.mindbriks.sparkle.firebase.DataSourceHelper;
import com.mindbriks.sparkle.interfaces.DataSource;
import com.mindbriks.sparkle.interfaces.DataSourceCallback;
import com.mindbriks.sparkle.model.Interest;
import com.mindbriks.sparkle.model.SaveDetailsModel;
import com.mindbriks.sparkle.sign_up_fragments.SignUpDrinkFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignUpGenderFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignUpHeightFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignUpInterestsFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignUpSmokeFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignupDobFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignupFullNameFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignupPhotoFragment;

import java.util.List;

public class SignUpDetailsActivity extends AppCompatActivity {

    private ViewPager signupViewPager;
    private SignUpPagerAdapter signUpPagerAdapter;
    private ImageView nextButton;
    private SignupFullNameFragment signupFullNameFragment;
    private SignupPhotoFragment signupPhotoFragment;
    private SignupDobFragment signupDobFragment;
    private SignUpGenderFragment signUpGenderFragment;
    private SignUpInterestsFragment signUpInterestsFragment;
    private SignUpHeightFragment signUpHeightFragment;
    private SignUpSmokeFragment signUpSmokeFragment;
    private SignUpDrinkFragment signUpDrinkFragment;
    private ProgressDialog mRegProgress;
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_details);

        // Get a reference to the ViewPager and set its adapter
        signupViewPager = findViewById(R.id.signup_view_pager);
        nextButton = findViewById(R.id.next_button);
        rootLayout = findViewById(R.id.root_layout);

        signupFullNameFragment = new SignupFullNameFragment();
        signupPhotoFragment = new SignupPhotoFragment();
        signupDobFragment = new SignupDobFragment();
        signUpGenderFragment = new SignUpGenderFragment();
        signUpInterestsFragment = new SignUpInterestsFragment();
        signUpHeightFragment = new SignUpHeightFragment();
        signUpSmokeFragment = new SignUpSmokeFragment();
        signUpDrinkFragment = new SignUpDrinkFragment();

        signUpPagerAdapter = new SignUpPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        signUpPagerAdapter.addFragment(signupFullNameFragment);
        signUpPagerAdapter.addFragment(signupPhotoFragment);
        signUpPagerAdapter.addFragment(signupDobFragment);
        signUpPagerAdapter.addFragment(signUpGenderFragment);
        signUpPagerAdapter.addFragment(signUpInterestsFragment);
        //TODO: Add education details activity
        signUpPagerAdapter.addFragment(signUpHeightFragment);
        signUpPagerAdapter.addFragment(signUpSmokeFragment);
        signUpPagerAdapter.addFragment(signUpDrinkFragment);

        signupViewPager.setAdapter(signUpPagerAdapter);

        nextButton.setOnClickListener(v -> {
            int currentItem = signupViewPager.getCurrentItem();
            if (currentItem < signUpPagerAdapter.getCount() - 1) {
                signupViewPager.setCurrentItem(currentItem + 1);
            } else {
                // handle form submission
                submitForm();
            }
        });
    }

    private void submitForm() {
        String userFullNameText = signupFullNameFragment.getFullName();
        Uri userProfilePictureUri = signupPhotoFragment.getProfileImageUri();
        long userDob = signupDobFragment.getDateOfBirthUnix();
        String userGender = signUpGenderFragment.getGenderPreference();
        List<Interest> userInterests = signUpInterestsFragment.getSelectedInterests();
        String userHeight = signUpHeightFragment.getUserHeight();
        String userSmokePreference = signUpSmokeFragment.getSmokingPreference();
        String userDrinkingPreference = signUpDrinkFragment.getDrinkingPreference();

        SaveDetailsModel saveDetailsModel = new SaveDetailsModel(userFullNameText, userProfilePictureUri, userDob, userGender, userInterests, userHeight, userSmokePreference, userDrinkingPreference);
        mRegProgress = new ProgressDialog(SignUpDetailsActivity.this, R.style.AppThemeDialog);
        mRegProgress.setIndeterminate(true);
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.setMessage("Saving user details...");
        mRegProgress.show();
        DataSource dataSource = DataSourceHelper.getDataSource();
        dataSource.saveDetails(saveDetailsModel, new DataSourceCallback() {
            @Override
            public void onSuccess() {
                mRegProgress.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                mRegProgress.dismiss();
                Snackbar.make(rootLayout, errorMessage, Snackbar.LENGTH_LONG).show();
            }
        });
    }


}