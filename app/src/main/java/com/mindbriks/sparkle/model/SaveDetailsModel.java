package com.mindbriks.sparkle.model;

import android.net.Uri;

import java.util.List;

public class SaveDetailsModel {
    String name;
    String gender;
    long dob;
    List<Interest> interests;

    Uri profileImageUri;
    String height;
    SmokingPreference smokePreference;
    DrinkingPreference drinkingPreference;

    public SaveDetailsModel(String userFullNameText, Uri userProfilePictureUri, long userDob, String userGender, List<Interest> userInterests, String userHeight, SmokingPreference userSmokePreference, DrinkingPreference userDrinkingPreference) {
        this.name = userFullNameText;
        this.profileImageUri = userProfilePictureUri;
        this.dob = userDob;
        this.gender = userGender;
        this.interests = userInterests;
        this.height = userHeight;
        this.smokePreference = userSmokePreference;
        this.drinkingPreference = userDrinkingPreference;
    }

    public SmokingPreference getSmokePreference() {
        return smokePreference;
    }

    public void setSmokePreference(SmokingPreference smokePreference) {
        this.smokePreference = smokePreference;
    }

    public DrinkingPreference getDrinkingPreference() {
        return drinkingPreference;
    }

    public void setDrinkingPreference(DrinkingPreference drinkingPreference) {
        this.drinkingPreference = drinkingPreference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public Uri getProfileImageUri() {
        return profileImageUri;
    }

    public void setProfileImageUri(Uri profileImageUri) {
        this.profileImageUri = profileImageUri;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

}
