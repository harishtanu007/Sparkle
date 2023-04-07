package com.mindbriks.sparkle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mindbriks.sparkle.adapter.SignUpPagerAdapter;
import com.mindbriks.sparkle.sign_up_fragments.SignUpDrinkFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignUpGenderFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignUpHeightFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignUpInterestsFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignUpSmokeFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignupDobFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignupFullNameFragment;
import com.mindbriks.sparkle.sign_up_fragments.SignupPhotoFragment;

public class SignUpActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Get a reference to the ViewPager and set its adapter
        signupViewPager = findViewById(R.id.signup_view_pager);
        nextButton = findViewById(R.id.next_button);

        signupFullNameFragment = new SignupFullNameFragment();
        signupPhotoFragment = new SignupPhotoFragment();
        signupDobFragment = new SignupDobFragment();
        signUpGenderFragment = new SignUpGenderFragment();
        signUpInterestsFragment = new SignUpInterestsFragment();
        signUpHeightFragment = new SignUpHeightFragment();
        signUpSmokeFragment = new SignUpSmokeFragment();
        signUpDrinkFragment = new SignUpDrinkFragment();

        signUpPagerAdapter = new SignUpPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        signUpPagerAdapter.addFragment(signUpDrinkFragment);
        signUpPagerAdapter.addFragment(signUpHeightFragment);
        signUpPagerAdapter.addFragment(signUpSmokeFragment);
        signUpPagerAdapter.addFragment(signUpInterestsFragment);
        signUpPagerAdapter.addFragment(signUpGenderFragment);
        signUpPagerAdapter.addFragment(signupFullNameFragment);
        signUpPagerAdapter.addFragment(signupPhotoFragment);
        signUpPagerAdapter.addFragment(signupDobFragment);


        signupViewPager.setAdapter(signUpPagerAdapter);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = signupViewPager.getCurrentItem();
                if (currentItem < signUpPagerAdapter.getCount() - 1) {
                    signupViewPager.setCurrentItem(currentItem + 1);
                } else {
                    // handle form submission
                    submitForm();
                }
            }
        });
    }

    private void submitForm() {
        String fullNameText = signupFullNameFragment.getFullName();

        // validate input and submit form
        // ...
    }


}