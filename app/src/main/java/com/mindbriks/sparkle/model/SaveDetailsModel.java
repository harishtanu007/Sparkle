package com.mindbriks.sparkle.model;

import android.net.Uri;

import java.util.List;

public class SaveDetailsModel {
    public String name;
    public String gender;
    public long dob;
    public List<Interest> interests;

    public Uri profileImageUri;
    public String height;
    String smokePreference;
    String drinkingPreference;

    public SaveDetailsModel(String userFullNameText, Uri userProfilePictureUri, long userDob, String userGender, List<Interest> userInterests, String userHeight, String userSmokePreference, String userDrinkingPreference) {
        this.name = userFullNameText;
        this.profileImageUri = userProfilePictureUri;
        this.dob = userDob;
        this.gender = userGender;
        this.interests = userInterests;
        this.height = userHeight;
        this.smokePreference = userSmokePreference;
        this.drinkingPreference = userDrinkingPreference;
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

    public String getSmokePreference() {
        return smokePreference;
    }

    public void setSmokePreference(String smokePreference) {
        this.smokePreference = smokePreference;
    }

    public String getDrinkingPreference() {
        return drinkingPreference;
    }

    public void setDrinkingPreference(String drinkingPreference) {
        this.drinkingPreference = drinkingPreference;
    }
}